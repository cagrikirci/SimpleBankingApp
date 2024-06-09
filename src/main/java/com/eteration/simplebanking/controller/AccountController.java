package com.eteration.simplebanking.controller;

// This class is a place holder you can change the complete implementation

import com.eteration.simplebanking.dto.Response;
import com.eteration.simplebanking.dto.TransactionRequest;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1/")
public class AccountController {

/*
    public Object getAccount() { return null; }
    public Object credit() { return null; }
    public Object debit() { return null; }
	*/

    @Autowired
    AccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<Response> credit(@PathVariable String accountNumber,
                                           @RequestBody TransactionRequest request) {
        double amount = request.getAmount();
        String approvalCode = accountService.credit(accountNumber, amount);
        Response response = new Response("OK", approvalCode);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<Response> debit(@PathVariable String accountNumber,
                                          @RequestBody TransactionRequest request) {
        double amount = request.getAmount();
        String approvalCode = accountService.debit(accountNumber,amount);
        Response response = new Response("OK", approvalCode);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}