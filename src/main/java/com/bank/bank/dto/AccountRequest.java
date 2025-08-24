package com.bank.bank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.bank.bank.exception.GlobalExceptionHandler.InvalidDecimalException;

import java.math.BigDecimal;

public class AccountRequest {
    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal initialBalance;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) {
        // Enforces at most 2 decimals in the balance
        if (initialBalance.scale() > 2) {
            throw new InvalidDecimalException("Initial balance cannot have more than 2 decimal places");
        }
        this.initialBalance = initialBalance;
    }
}
