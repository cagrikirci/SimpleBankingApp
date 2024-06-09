package com.eteration.simplebanking.services;


// This class is a place holder you can change the complete implementation

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;


    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).
                orElseThrow( () -> new RuntimeException("Hesap bulunamadı."));
    }

    @Transactional
    public String credit(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber).
                orElseThrow(() -> new RuntimeException("Hesap bulunamadı."));
        account.setBalance(account.getBalance() + amount);
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        depositTransaction.setAccount(account);
        transactionRepository.save(depositTransaction);
        accountRepository.save(account);
        return depositTransaction.getApprovalCode();
    }

    @Transactional
    public String debit(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber).
                orElseThrow(() -> new RuntimeException("Hesap bulunamadı."));
        if(account.getBalance() < amount) {
            throw new InsufficientBalanceException("Yetersiz bakiye");
        }
        account.setBalance(account.getBalance() - amount);
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount);
        withdrawalTransaction.setAccount(account);
        account.getTransactionList().add(withdrawalTransaction);
        transactionRepository.save(withdrawalTransaction);
        accountRepository.save(account);
        return withdrawalTransaction.getApprovalCode();
    }
}
