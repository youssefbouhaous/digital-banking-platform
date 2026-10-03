# Digital Banking Platform

A REST backend for a small digital bank, built with Spring Boot and PostgreSQL.

## The idea

The platform models the core of a retail bank:

- **Customers** own **accounts**.
- Each account holds a **balance** and has a **lifecycle state** (`ACTIVE`, `FROZEN`, `DORMANT`, `CLOSED`).
  New accounts start out `FROZEN` and must be activated before use.
- Money moves in two ways:
  - **Operations** on a single account: deposits and withdrawals.
  - **Transfers** between two accounts. Each transfer is atomic: the debit and the credit happen in one database transaction.
- Every movement of money and every state change leaves a persisted record. That gives each account a full, auditable history.

The goal is a clean, layered backend (controller -> service -> repository) with explicit business errors: account not found, insufficient funds, invalid transfer.

## Domain model

| Entity | Purpose |
|---|---|
| `Customer` | Person owning one or more accounts (name, unique email, phone). |
| `Account` | Bank account: username, holder name, balance, lifecycle `state`, optimistic-lock `version`, optional owning `customer`. |
| `Transfer` | Receipt of a money transfer between a sender and a receiver account. |
| `AccountOperation` | Ledger entry for a `DEPOSIT` or `WITHDRAWAL` on one account, with the resulting balance. |
| `AccountStateHistory` | Audit trail of lifecycle changes (previous state, new state, reason, timestamp). |

Enums: `AccountState`, `AccountType`, `OperationType`.

```
Customer 1 ──── * Account 1 ──── * AccountOperation
                    │  1
                    ├──── * AccountStateHistory
                    │
                    └── sender / receiver ──── * Transfer
```

## Tech stack

- Java 17, Spring Boot 4 (Web MVC, Data JPA, Validation)
- PostgreSQL (Hibernate, `ddl-auto=update`)
- Lombok
- Maven (wrapper included)

## Project structure

```
src/main/java/com/dbp/digitalbankingplatform
├── controller   REST endpoints
├── service      business logic
├── repository   Spring Data JPA repositories
├── model        JPA entities and enums
├── dto          request / response records
├── exception    business exceptions
└── utils        startup data seeding (20 demo accounts)
```

## API

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/accounts` | Create an account (`userName`, `firstName`, `lastName`). |
| `GET` | `/api/accounts` | List all accounts. |
| `GET` | `/api/accounts/{id}/balance` | Get an account's balance. |
| `GET` | `/api/accounts/{accountId}/transfers?page=&size=` | Paginated transfer history (each entry tagged `INCOMING` / `OUTGOING`). |
| `POST` | `/api/transfers` | Transfer money (`senderId`, `receiverId`, `amount`). |
| `GET` | `/api/accounts/lifecyle/{id}/getlifecycle` | Get an account's lifecycle state. |
| `PUT` | `/api/accounts/lifecyle/{id}/updatelifecycle?state=ACTIVE` | Change an account's lifecycle state. |

Error codes: `404` account not found, `409` insufficient funds, `400` invalid transfer or validation failure.

## Running locally

1. Start PostgreSQL and create a database.
2. Export the connection settings:
   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/digital_banking
   export DB_USERNAME=postgres
   export DB_PASSWORD=secret
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   On first start the schema is created automatically and 20 demo accounts are seeded.

Run the tests with `./mvnw test`.
