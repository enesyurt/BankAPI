package com.banking.bankapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class BankAccountResponse {
    private Long id;
    private String ownerName;
    private double balance;
}
