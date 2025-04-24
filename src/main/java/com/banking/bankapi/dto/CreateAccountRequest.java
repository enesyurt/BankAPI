package com.banking.bankapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {
    private String ownerName;
    private double initialDeposit;
}
