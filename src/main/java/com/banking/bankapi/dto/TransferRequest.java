package com.banking.bankapi.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TransferRequest {
    @Positive(message = "Source account ID must be a positive number")
    private long fromAccountId;

    @Positive(message = "Target account ID must ve a positive number")
    private long toAccountId;

    @Positive(message = "Transfer amount must be positive")
    private double amount;
}
