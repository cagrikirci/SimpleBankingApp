package com.eteration.simplebanking;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCredit() {
        String accountNumber = "669-7788";
        double amount = 1000.0;
        Account account = new Account(LocalDateTime.now());
        account.setAccountNumber(accountNumber);
        account.setBalance(0.0);


        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(DepositTransaction.class))).thenAnswer(i -> i.getArguments()[0]);
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        String approvalCode = service.credit(accountNumber, amount);

        assertNotNull(approvalCode);
        assertEquals(1000.0, account.getBalance());
        verify(transactionRepository, times(1)).save(any(DepositTransaction.class));
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testDebit() {
        String accountNumber = "669-7788";
        double amount = 50.0;
        Account account = new Account(LocalDateTime.now());
        account.setAccountNumber(accountNumber);
        account.setBalance(1000.0);

        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(WithdrawalTransaction.class))).thenAnswer(i -> i.getArguments()[0]);
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        String approvalCode = service.debit(accountNumber, amount);

        assertNotNull(approvalCode);
        assertEquals(950.0, account.getBalance());
        verify(transactionRepository, times(1)).save(any(WithdrawalTransaction.class));
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testDebitInsufficientBalance() {
        String accountNumber = "669-7788";
        double amount = 1000.0;
        Account account = new Account(LocalDateTime.now());
        account.setAccountNumber(accountNumber);
        account.setBalance(500.0);

        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            service.debit(accountNumber, amount);
        });

        assertEquals("Yetersiz bakiye", exception.getMessage());
        verify(transactionRepository, never()).save(any(WithdrawalTransaction.class));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testGetAccount(){
        String accountNumber = "669-7788";
        Account account = new Account(LocalDateTime.now());
        account.setAccountNumber(accountNumber);
        account.setOwner("Kerem Karaca");
        account.setBalance(950.0);

        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        Account result = service.getAccount(accountNumber);

        assertNotNull(accountNumber);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals("Kerem Karaca", result.getOwner());
        assertEquals(950.0, result.getBalance());
    }

    @Test
    void testGetAccountNotFound() {
        String accountNumber = "669-7788";

        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.getAccount(accountNumber);
        });

        assertEquals("Hesap bulunamadı.", exception.getMessage());
    }
}
