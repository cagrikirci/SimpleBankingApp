package com.eteration.simplebanking;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionTest {

    private Transaction transaction;

    class TestTransaction extends Transaction {
        public TestTransaction(double amount) {
            super(amount);
        }
    }

    @BeforeEach
    void setUp() {
        transaction = new TestTransaction(100.0);
    }

    @Test
    void testConstructor() {
        assertNotNull(transaction.getDate());
        assertEquals(100.0, transaction.getAmount());
        assertNotNull(transaction.getApprovalCode());
    }

    @Test
    void testSetAndGetId() {
        transaction.setId(1L);
        assertEquals(1l, transaction.getId());
    }

    @Test
    void testSetAndGetDate() {
        LocalDateTime now = LocalDateTime.now();
        transaction.setDate(now);
        assertEquals(now, transaction.getDate());
    }

    @Test
    void testSetAndGetAmount() {
        transaction.setAmount(200.0);
        assertEquals(200.0, transaction.getAmount());
    }

    @Test
    void testSetAndGetApprovalCode() {
        String approvalCode = UUID.randomUUID().toString();
        transaction.setApprovalCode(approvalCode);
        assertEquals(approvalCode, transaction.getApprovalCode());
    }

    @Test
    void testSetAndGetAccount() {
        Account account = new Account(LocalDateTime.now());
        transaction.setAccount(account);
        assertEquals(account, transaction.getAccount());
    }










}
