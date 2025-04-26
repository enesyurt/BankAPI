package com.banking.bankapi.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountRequest {
    private String ownerName;

    @PositiveOrZero(message = "Balance cannot be negative")
    private Double balance;
}
