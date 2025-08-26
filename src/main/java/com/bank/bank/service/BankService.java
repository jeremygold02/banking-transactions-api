package com.bank.bank.service;

import com.bank.bank.dto.*;
import com.bank.bank.exception.GlobalExceptionHandler.AccountNotFoundException;
import com.bank.bank.exception.GlobalExceptionHandler.InsufficientFundsException;
import com.bank.bank.exception.GlobalExceptionHandler.InvalidTransactionException;
import com.bank.bank.model.Account;
import com.bank.bank.model.Transaction;
import com.bank.bank.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private final BankRepository repository;

    // Constructor injection
    public BankService(BankRepository repository) {
        this.repository = repository;
    }

    // Create account
    public AccountResponse createAccount(AccountRequest request) {
        Account account = repository.saveAccount(request.getName(), request.getInitialBalance());
        return new AccountResponse(account.getId(), account.getName(), account.getBalance());
    }

    // Transfer funds
    public void transfer(TransferRequest request) {
        Account from = repository.findAccount(request.getFromAccount())
                .orElseThrow(() -> new AccountNotFoundException(request.getFromAccount()));

        Account to = repository.findAccount(request.getToAccount())
                .orElseThrow(() -> new AccountNotFoundException(request.getToAccount()));

        if (from.getId().equals(to.getId())) {
            throw new InvalidTransactionException("Cannot transfer to the same account");
        }
        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(from.getId());
        }

        // Update balances
        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        // Save transaction in repository
        Transaction tx = new Transaction(from.getId(), to.getId(), request.getAmount());
        repository.saveTransaction(tx);
    }

    // List transactions for an account
    public List<TransactionResponse> getTransactions(Long accountId) {
        List<Transaction> txs = repository.findTransactions(accountId);

        return txs.stream().map(t -> {
            TransactionResponse resp = new TransactionResponse();
            resp.setFromAccount(t.getFromAccount());
            resp.setFromAccountName(repository.findAccount(t.getFromAccount())
                    .map(Account::getName).orElse("Unknown"));
            resp.setToAccount(t.getToAccount());
            resp.setToAccountName(repository.findAccount(t.getToAccount())
                    .map(Account::getName).orElse("Unknown"));
            resp.setAmount(t.getAmount());
            resp.setTimestamp(t.getTimestamp());
            return resp;
        }).toList();
    }

    // Show account information
    public AccountResponse getAccountById(Long id) {
        Account account = repository.findAccount(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return new AccountResponse(account.getId(), account.getName(), account.getBalance());
    }

    // List all accounts in memory
    public List<AccountResponse> getAllAccounts() {
        return repository.findAllAccounts().stream()
                .map(acc -> new AccountResponse(acc.getId(), acc.getName(), acc.getBalance()))
                .toList();
    }
}
