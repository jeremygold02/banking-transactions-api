package com.bank.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    // Account not found/doesn't exist
    public static class AccountNotFoundException extends RuntimeException {
        public AccountNotFoundException(Long id) {
            super("Account not found: " + id);
        }
    }

    // Insufficient funds
    public static class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(Long id) {
            super("Insufficient funds in account: " + id);
        }
    }

    // Invalid amount
    public static class InvalidAmountException extends RuntimeException {
        public InvalidAmountException(String message) {
            super(message);
        }
    }

    // Invalid decimal (>2 decimals)
    public static class InvalidDecimalException extends RuntimeException {
        public InvalidDecimalException(String message) {
            super(message);
        }
    }

    // Invalid transaction
    public static class InvalidTransactionException extends RuntimeException {
        public InvalidTransactionException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccountNotFound(AccountNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientFunds(InsufficientFundsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<Map<String, String>> handleInvalidAmount(InvalidAmountException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDecimalException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDecimal(InvalidDecimalException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTransaction(InvalidTransactionException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonParseError(HttpMessageNotReadableException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        if (message.contains("Initial balance cannot have more than 2 decimal places")) {
            return ResponseEntity.badRequest().body(Map.of("error", message));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid request body"));
    }
}
