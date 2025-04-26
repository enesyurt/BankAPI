package com.banking.bankapi.service;

import java.util.List;
import java.util.stream.Collectors;

import com.banking.bankapi.exception.AccountNotFoundException;
import com.banking.bankapi.model.BankAccount;
import com.banking.bankapi.dto.BankAccountResponse;
import com.banking.bankapi.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    // Create a new account
    public BankAccount createAccount(String ownerName, double initialDeposit) {
        BankAccount account = new BankAccount(ownerName, initialDeposit);
        return bankAccountRepository.save(account);
    }

    // Delete account
    public void deleteAccount(Long accountId) {
        if (bankAccountRepository.existsById(accountId)) {
           bankAccountRepository.deleteById(accountId);
        }
        else {
            throw new AccountNotFoundException("Account with ID " + accountId + "not found.");
        }
    }


    // Get account balance
    public double checkBalance(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getBalance();
    }

    // Deposit money into an account
    @Transactional
    public BankAccount deposit(Long accountId, double amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        account.setBalance(account.getBalance() + amount);
        return bankAccountRepository.save(account);
    }

    // Withdraw money from an account
    @Transactional
    public BankAccount withdraw(Long accountId, double amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (amount <= 0) {
            throw new IllegalArgumentException(("Withdrawal amount must be positive"));
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        return bankAccountRepository.save(account);
    }

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, double amount){
        BankAccount sourceAccount = bankAccountRepository.findById((fromAccountId))
                .orElseThrow(()-> new IllegalArgumentException("Account not found"));
        BankAccount destinationAccount = bankAccountRepository.findById((toAccountId))
                .orElseThrow(()-> new IllegalArgumentException("Account not found"));
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (sourceAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        // Save both accounts
        bankAccountRepository.saveAll(List.of(sourceAccount, destinationAccount));

        // Return the list of both accounts
       // return List.of(sourceAccount, destinationAccount); // returns a List of BankAccount
    }

    public List<BankAccountResponse> getAllAccounts() {
        return bankAccountRepository.findAll().stream().map(account -> {
            BankAccountResponse response = new BankAccountResponse();
            response.setId(account.getId());
            response.setOwnerName(account.getOwnerName());
            response.setBalance((account.getBalance()));
            return response;
        }).collect(Collectors.toList());
    }
}