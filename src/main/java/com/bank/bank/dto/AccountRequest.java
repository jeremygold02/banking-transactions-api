package com.bank.bank.dto;

import java.math.BigDecimal;

public class AccountRequest {
    private String name;
    private BigDecimal initialBalance;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
