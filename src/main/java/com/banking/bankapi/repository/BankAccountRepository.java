package com.banking.bankapi.repository;

import com.banking.bankapi.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    // You can add custom queries here if needed in the future
}
