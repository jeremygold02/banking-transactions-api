package com.bank.bank.dto;

import java.math.BigDecimal;

public class TransactionRequest {
    private Long fromAccount;
    private Long toAccount;
    private BigDecimal amount;

    public Long getFromAccount() { return fromAccount; }
    public void setFromAccount(Long fromAccount) { this.fromAccount = fromAccount; }

    public Long getToAccount() { return toAccount; }
    public void setToAccount(Long toAccount) { this.toAccount = toAccount; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
