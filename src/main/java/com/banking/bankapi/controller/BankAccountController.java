package com.banking.bankapi.controller;

import com.banking.bankapi.model.BankAccount;
import com.banking.bankapi.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.banking.bankapi.dto.CreateAccountRequest;


@RestController
@RequestMapping("/bankaccounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    // Constructor-based dependency injection
    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // POST: Create a new bank account
    @PostMapping
    public BankAccount createAccount(@RequestBody CreateAccountRequest request) {
        return bankAccountService.createAccount(request.getOwnerName(), request.getInitialDeposit());
    }

    // GET: Get account balance
    @GetMapping("/{accountId}/balance")
    public double getAccountBalance(@PathVariable long accountId) {
        return bankAccountService.checkBalance(accountId);
    }

}