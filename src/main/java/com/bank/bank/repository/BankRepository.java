package com.bank.bank.repository;

import com.bank.bank.model.Account;
import com.bank.bank.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.math.BigDecimal;

@Repository
public class BankRepository {
    private final Map<Long, Account> accounts = new HashMap<>();
    private final Map<Long, List<Transaction>> transactions = new HashMap<>();
    private Long accountIdCounter = 1L;

    public synchronized Account saveAccount(String name, BigDecimal initialBalance) {
        Account account = new Account(accountIdCounter++, name, initialBalance);
        accounts.put(account.getId(), account);
        transactions.put(account.getId(), new ArrayList<>());
        return account;
    }

    public Optional<Account> findAccount(Long id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public void saveTransaction(Transaction tx) {
        transactions.get(tx.getFromAccount()).add(tx);
        transactions.get(tx.getToAccount()).add(tx);
    }

    public List<Transaction> findTransactions(Long accountId) {
        return transactions.getOrDefault(accountId, Collections.emptyList());
    }

    public List<Account> findAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
}
