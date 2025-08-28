package com.bank.bank.controller;

import com.bank.bank.dto.*;
import com.bank.bank.service.BankService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankController {
    private final BankService service;

    public BankController(BankService service) {
        this.service = service;
    }

    // Create account
    @PostMapping("/create")
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest request) {
        return service.createAccount(request);
    }

    // Transfer funds
    @PostMapping("/transfer")
    public String transfer(@Valid @RequestBody TransactionRequest request) {
        service.transfer(request);
        return "Transfer successful!";
    }

    // List transactions
    @GetMapping("/{accountId}/transactions")
    public List<TransactionResponse> getTransactions(@PathVariable Long accountId) {
        return service.getTransactions(accountId);
    }

    // Show account information
    @GetMapping("/{accountId}")
    public AccountResponse getAccountById(@PathVariable Long accountId) {
        return service.getAccountById(accountId);
    }

    // List all accounts in memory
    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return service.getAllAccounts();
    }
}
