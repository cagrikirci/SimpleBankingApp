package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.dto.Response;
import com.eteration.simplebanking.dto.TransactionRequest;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.services.AccountService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Objects;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests  {



    @Spy
    @InjectMocks
    private AccountController controller;
 
    @Mock
    private AccountService service;

    @Test
    void testGetAccount() {
        String accountNumber = "669-7788";
        Account account = new Account(LocalDateTime.now());
        account.setAccountNumber(accountNumber);
        account.setOwner("Kerem Karaca");
        account.setBalance(950);

        when(service.getAccount(anyString())).thenReturn(account);

        ResponseEntity<Account> responseEntity = controller.getAccount(accountNumber);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(accountNumber, Objects.requireNonNull(responseEntity.getBody()).getAccountNumber());
        assertEquals("Kerem Karaca", responseEntity.getBody().getOwner());
        assertEquals(950.0, responseEntity.getBody().getBalance());
    }

    @Test
    void testCredit() {
        String accountNumber = "669-7788";
        double amount = 1000.0;
        String approvalCode = "123e4567-e89b-42d3-a456-556642440000";

        when(service.credit(anyString(), anyDouble())).thenReturn(approvalCode);

        TransactionRequest request = new TransactionRequest();
        request.setAmount(amount);

        ResponseEntity<Response> responseEntity = controller.credit(accountNumber, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("OK", responseEntity.getBody().getStatus());
        assertEquals(approvalCode, responseEntity.getBody().getApprovalCode());
    }

    void testDebit() {
        String accountNumber = "669-7788";
        double amount = 70.0;
        String approvalCode = "432e1234-e89b-42d3-a456-883353551111";

        when(service.credit(anyString(), anyDouble())).thenReturn(approvalCode);

        TransactionRequest request = new TransactionRequest();
        request.setAmount(amount);

        ResponseEntity<Response> responseEntity = controller.credit(accountNumber, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("OK", responseEntity.getBody().getStatus());
        assertEquals(approvalCode, responseEntity.getBody().getApprovalCode());
    }

    @Test
    void testDebitInsufficientBalance() {
        String accountNumber = "669-7788";
        double amount = 1000.0;

        when(service.credit(anyString(), anyDouble())).
                thenThrow(new InsufficientBalanceException("Yetersiz bakiye"));

        TransactionRequest request = new TransactionRequest();
        request.setAmount(amount);

        try{
            controller.debit(accountNumber, request);
        }catch (InsufficientBalanceException exception) {
            assertEquals("Yetersiz Bakiye", exception.getMessage());
        }

    }

    /*

    @Test
    public void givenId_Credit_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        verify(service, times(1)).findAccount("17892");
        assertEquals("OK", result.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransaction(50.0));
        verify(service, times(2)).findAccount("17892");
        assertEquals("OK", result.getBody().getStatus());
        assertEquals("OK", result2.getBody().getStatus());
        assertEquals(950.0, account.getBalance(),0.001);
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson()
    throws Exception {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            Account account = new Account("Kerem Karaca", "17892");

            doReturn(account).when(service).findAccount( "17892");
            ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
            assertEquals("OK", result.getBody().getStatus());
            assertEquals(1000.0, account.getBalance(),0.001);
            verify(service, times(1)).findAccount("17892");

            ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransaction(5000.0));
        });
    }

    @Test
    public void givenId_GetAccount_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<Account> result = controller.getAccount( "17892");
        verify(service, times(1)).findAccount("17892");
        assertEquals(account, result.getBody());
    }


     */

}
