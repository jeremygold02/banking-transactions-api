package com.bank.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Long fromAccount;
    private Long toAccount;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public Transaction(Long fromAccount, Long toAccount, BigDecimal amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public Long getFromAccount() { return fromAccount; }
    public Long getToAccount() { return toAccount; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
