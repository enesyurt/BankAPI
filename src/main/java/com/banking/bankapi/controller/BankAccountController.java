package com.banking.bankapi.controller;

import com.banking.bankapi.dto.*;
import com.banking.bankapi.exception.AccountNotFoundException;
import com.banking.bankapi.model.BankAccount;
import com.banking.bankapi.service.BankAccountService;
import org.apache.coyote.Response;
import org.hibernate.event.internal.DefaultPostLoadEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/bankaccounts")
@Validated
public class BankAccountController {

    private final BankAccountService bankAccountService;

    // Constructor-based dependency injection
    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // POST: Create a new bank account
    // API: http://localhost:8080/bankaccounts
    @PostMapping
    public BankAccount createAccount(@RequestBody CreateAccountRequest request) {
        return bankAccountService.createAccount(request.getOwnerName(), request.getInitialDeposit());
    }

    // DELETE: Delete Bank Account by ID
    // API: http://localhost:8080/bankaccounts/1/delete
    @DeleteMapping("/{accountId}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        try {
            bankAccountService.deleteAccount(accountId);
            return ResponseEntity.ok("Account deleted successfully.");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    // GET: Get account balance
    // API: http://localhost:8080/bankaccounts/1/balance
    @GetMapping("/{accountId}/balance")
    public double getAccountBalance(@PathVariable long accountId) {
        return bankAccountService.checkBalance(accountId);
    }

    // POST: Withdraw money
    // API: http://localhost:8080/bankaccounts/1/withdraw
    @PostMapping("/{accountId}/withdraw")
    public BankAccount withdraw(@PathVariable Long accountId, @RequestParam double amount) {
        return bankAccountService.withdraw(accountId, amount);
    }

    // POST: Transfer Money
    // API: http://localhost:8080/bankaccounts/transfer
    @PostMapping("/transfer")
    public String transfer(@Valid @RequestBody TransferRequest request) {
        bankAccountService.transfer(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
        return "Transfer successful.";
    }

    // POST: Deposit Money
    // API: http://localhost:8080/bankaccounts/1/deposit
    @PostMapping("/{accountId}/deposit")
    public BankAccount deposit(@PathVariable Long accountId, @RequestBody @Valid DepositRequest request) {
        return bankAccountService.deposit(accountId, request.getAmount());
    }

    // GET: Get account info
    // API: http://localhost:8080/bankaccounts
    @GetMapping
    public List<BankAccountResponse> getAllAccounts() {
        return bankAccountService.getAllAccounts();
    }

    // PUT: Update account details.
    // API: http://localhost:8080/bankaccounts/1/update
    @PutMapping("/{accountId}/update")
    public BankAccount updateAccount(@PathVariable Long accountId, @RequestBody UpdateAccountRequest request) {
        return bankAccountService.updateAccount(accountId, request);
    }


}