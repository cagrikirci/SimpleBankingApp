package com.eteration.simplebanking;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(LocalDateTime.now());
    }

    @Test
    void testConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Account account = new Account(now);

        assertNotNull(account.getCreateDate());
        assertEquals(now, account.getCreateDate());
        assertEquals(0, account.getBalance());
        assertNotNull(account.getTransactionList());
        assertTrue(account.getTransactionList().isEmpty());
    }

    @Test
    void testSetAndGetId() {
        account.setId(1L);
        assertEquals(1L, account.getId());
    }

    @Test
    void testSetAndGetOwner() {
        account.setOwner("Kerem Karaca");
        assertEquals("Kerem Karaca", account.getOwner());
    }

    @Test
    void testSetAndGetAccountNumber() {
        account.setAccountNumber("669-7788");
        assertEquals("669-7788", account.getAccountNumber());
    }

    @Test
    void testSetAndGetBalance() {
        account.setBalance(1000.0);
        assertEquals(1000.0, account.getBalance());
    }

    @Test
    void testSetAndGetCreateDate() {
        LocalDateTime now = LocalDateTime.now();
        account.setCreateDate(now);
        assertEquals(now, account.getCreateDate());
    }

    @Test
    void testSetAndGetTransactionList() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction(100.0) {
        };
        transactions.add(transaction);

        account.setTransactionList(transactions);
        assertEquals(transactions, account.getTransactionList());
        assertFalse(account.getTransactionList().isEmpty());
        assertEquals(1, account.getTransactionList().size());
        assertEquals(transaction, account.getTransactionList().get(0));
    }

    @Test
    void testAddTransaction() {
        Transaction transaction = new Transaction(100.0) {
        };
        account.getTransactionList().add(transaction);

        assertFalse(account.getTransactionList().isEmpty());
        assertEquals(1, account.getTransactionList().size());
        assertEquals(transaction, account.getTransactionList().get(0));
    }
}
