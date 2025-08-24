package com.bank.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private Long fromAccount;
    private String fromAccountName;
    private Long toAccount;
    private String toAccountName;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public Long getFromAccount() { return fromAccount; }
    public void setFromAccount(Long fromAccount) { this.fromAccount = fromAccount; }

    public String getFromAccountName() { return fromAccountName; }
    public void setFromAccountName(String fromAccountName) { this.fromAccountName = fromAccountName; }

    public Long getToAccount() { return toAccount; }
    public void setToAccount(Long toAccount) { this.toAccount = toAccount; }

    public String getToAccountName() { return toAccountName; }
    public void setToAccountName(String toAccountName) { this.toAccountName = toAccountName; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
