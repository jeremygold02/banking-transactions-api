# Banking Transactions API
A simple RESTful API for managing user accounts and banking transactions. This project was implemented in Java using Spring Boot with in-memory storage.

## Assumptions
- All balances use 2 decimal places
- Accounts cannot have negative balances
- Transfers must be between two different accounts
- Only positive amounts can be deposited, withdrawn, or transferred
- Data is stored in memory
- No authentication or security is implemented

## Features
- Create a new user account with an initial balance
- Transfer funds between accounts
- Retrieve transaction history for a given account
- Input validation and error handling for invalid data and insufficient funds

## Requirements
- Java 17+
- Maven 3.8+
- Spring Boot


## Build & Run
1. Clone the repository

```
git clone https://github.com/jeremygold02/banking-transactions-api.git
cd banking-transactions-api
```

2. Build the project
```
mvn clean install
```

3. Run the application
```
mvn spring-boot:run
```

The API will start on http://localhost:8080


## API Reference

#### Create Account

```
  POST /api/accounts/create
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Required.** Name of account |
| `initialBalance` | `number` | **Required.** Initial balance |

Example:
```
curl -X POST http://localhost:8080/api/accounts/create \
  -H "Content-Type: application/json" \
  -d '{"name":"Jeremy","initialBalance":123.45}'
```

Constraints & Errors

- *initialBalance* must be non-negative.

- If *initialBalance* < 0, API returns **400 Bad Request**.

---

#### Get Account Details

```
  GET /api/accounts/{accountId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `accountId`      | `integer` | **Required**. ID of the account. |

Example:
```
curl -X GET http://localhost:8080/api/accounts/1
```

Constraints & Errors

- If account does not exist, returns **404 Not Found**.

---

#### List All Accounts

```
  GET /api/accounts
```

No parameters. Returns all accounts in memory. If no accounts exist, returns an empty list.

---

#### Transfer Funds

```
  POST /api/accounts/transfer
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `fromAccount` | `integer` | **Required.** ID of the sending account |
| `toAccount` | `integer` | **Required.** ID of the receiving account |
| `amount` | `integer` | **Required.** Amount to transfer |

Example:
```
curl -X POST http://localhost:8080/api/accounts/transfer \
  -H "Content-Type: application/json" \
  -d '{"fromAccount":1,"toAccount":2,"amount":100}'
```

Constraints & Errors:

- Both accounts must exist. Otherwise, **400 Bad Request**.

- Transfers cannot be made from an account to itself. Otherwise, **400 Bad Request**.

- *amount* must be positive.

- If *fromAccount* does not have sufficient funds, API returns **400 Bad Request** with "Insufficient funds".

---

#### Get Transaction History

```
  GET /api/accounts/{accountId}/transactions
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `accountId`      | `integer` | **Required**. ID of the account. |

Example:
```
curl -X GET http://localhost:8080/api/accounts/1/transactions
```

Constraints & Errors

- If account does not exist, returns **404 Not Found**.

- Returns an empty list if no transactions exist for the account.