package com.banking.bankapi.dto;

import jakarta.validation.constraints.Positive;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositRequest {
    @Positive(message = "Deposit amount must be positive")
    private double amount;
    }
