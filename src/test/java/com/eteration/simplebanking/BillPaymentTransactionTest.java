package com.eteration.simplebanking;


import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.BillPaymentTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class BillPaymentTransactionTest {

    private BillPaymentTransaction billPaymentTransaction;

    @BeforeEach
    void setUp() {
        billPaymentTransaction = new BillPaymentTransaction(200.0, "Utility Company");
    }

    @Test
    void testConstructor() {
        assertNotNull(billPaymentTransaction.getDate());
        assertEquals(200.0, billPaymentTransaction.getAmount());
        assertNotNull(billPaymentTransaction.getApprovalCode());
    }

    @Test
    void testSetAndGetId() {
        billPaymentTransaction.setId(1L);
        assertEquals(1L, billPaymentTransaction.getId());
    }

    @Test
    void testSetAndGetDate() {
        LocalDateTime now = LocalDateTime.now();
        billPaymentTransaction.setDate(now);
        assertEquals(now, billPaymentTransaction.getDate());
    }

    @Test
    void testSetAndGetAmount() {
        billPaymentTransaction.setAmount(300.0);
        assertEquals(300.0, billPaymentTransaction.getAmount());
    }

    @Test
    void testSetAndGetApprovalCode() {
        String approvalCode = "test-approval-code";
        billPaymentTransaction.setApprovalCode(approvalCode);
        assertEquals(approvalCode, billPaymentTransaction.getApprovalCode());
    }

    @Test
    void testSetAndGetAccount() {
        Account account = new Account(LocalDateTime.now());
        billPaymentTransaction.setAccount(account);
        assertEquals(account, billPaymentTransaction.getAccount());
    }
}
