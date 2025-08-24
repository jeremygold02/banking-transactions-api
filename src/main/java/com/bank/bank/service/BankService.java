package com.bank.bank.service;

import com.bank.bank.dto.*;
import com.bank.bank.exception.GlobalExceptionHandler.AccountNotFoundException;
import com.bank.bank.exception.GlobalExceptionHandler.InsufficientFundsException;
import com.bank.bank.exception.GlobalExceptionHandler.InvalidTransactionException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BankService {
    private final Map<Long, Account> accounts = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private long nextAccountId = 1;

    // Create account
    public AccountResponse createAccount(AccountRequest request) {
        long id = nextAccountId++;
        Account account = new Account(id, request.getName(), request.getInitialBalance());
        accounts.put(id, account);

        return new AccountResponse(id, request.getName(), request.getInitialBalance());
    }

    // Transfer funds
    public void transfer(TransferRequest request) {
        Account from = accounts.get(request.getFromAccount());
        Account to = accounts.get(request.getToAccount());

        if (from == null) {
            throw new AccountNotFoundException(request.getFromAccount());
        }
        if (to == null) {
            throw new AccountNotFoundException(request.getToAccount());
        }
        if (from.getId().equals(to.getId())) {
            throw new InvalidTransactionException("Cannot transfer to the same account");
        }
        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(from.getId());
        }

        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        transactions.add(new Transaction(from.getId(), to.getId(), request.getAmount(), LocalDateTime.now()));
    }

    // List transactions for an account
    public List<TransactionResponse> getTransactions(Long accountId) {
        List<TransactionResponse> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getFromAccount().equals(accountId) || t.getToAccount().equals(accountId)) {
                TransactionResponse resp = new TransactionResponse();
                resp.setFromAccount(t.getFromAccount());
                resp.setFromAccountName(accounts.get(t.getFromAccount()).getName());
                resp.setToAccount(t.getToAccount());
                resp.setToAccountName(accounts.get(t.getToAccount()).getName());
                resp.setAmount(t.getAmount());
                resp.setTimestamp(t.getTimestamp());
                result.add(resp);
            }
        }
        return result;
    }

    // Show account information
    public AccountResponse getAccountById(Long id) {
        Account account = accounts.get(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        return new AccountResponse(account.getId(), account.getName(), account.getBalance());
    }

    // List all accounts in memory
    public List<AccountResponse> getAllAccounts() {
        return accounts.values().stream()
                .map(acc -> new AccountResponse(acc.getId(), acc.getName(), acc.getBalance()))
                .toList();
    }

    // Account class
    private static class Account {
        private final Long id;
        private final String name;
        private BigDecimal balance;

        public Account(Long id, String name, BigDecimal balance) {
            this.id = id;
            this.name = name;
            this.balance = balance;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public BigDecimal getBalance() { return balance; }
        public void setBalance(BigDecimal balance) { this.balance = balance; }
    }

    // Transaction class
    private static class Transaction {
        private final Long fromAccount;
        private final Long toAccount;
        private final BigDecimal amount;
        private final LocalDateTime timestamp;

        public Transaction(Long fromAccount, Long toAccount, BigDecimal amount, LocalDateTime timestamp) {
            this.fromAccount = fromAccount;
            this.toAccount = toAccount;
            this.amount = amount;
            this.timestamp = timestamp;
        }

        public Long getFromAccount() { return fromAccount; }
        public Long getToAccount() { return toAccount; }
        public BigDecimal getAmount() { return amount; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}
