package com.dbp.digitalbankingplatform.controller;

import com.dbp.digitalbankingplatform.dto.request.CreateAccountRequest;
import com.dbp.digitalbankingplatform.dto.response.AccountResponse;
import com.dbp.digitalbankingplatform.dto.response.TransferByAccountResponse;
import com.dbp.digitalbankingplatform.exception.AccountNotActiveException;
import com.dbp.digitalbankingplatform.exception.AccountNotFoundException;
import com.dbp.digitalbankingplatform.exception.InsufficientFundsException;
import com.dbp.digitalbankingplatform.exception.InvalidTransferException;
import com.dbp.digitalbankingplatform.model.AccountOperation;
import com.dbp.digitalbankingplatform.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * AccountController
 *
 * @author Youssef Bouhaous
 * @since 9/25/26
 */
@RequestMapping("/api/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest createAccountRequest){
        AccountResponse accountSaved = new AccountResponse(accountService.create(createAccountRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(accountSaved);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAll(){
        return ResponseEntity.ok(AccountResponse.fromAccounts(accountService.findAll()));
    }

    @GetMapping("{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID id){
        return ResponseEntity.ok(accountService.checkBalance(id));
    }


    @GetMapping("{accountId}/transfers")
    public ResponseEntity<?> historyTransferByAccount(@PathVariable UUID accountId,  @PageableDefault(page = 0, size = 20) Pageable pageable){
        try {
            Page<TransferByAccountResponse> accountResponses = accountService.historyByAccount(accountId,pageable);
            return ResponseEntity.ok(accountResponses);
        }
        catch (AccountNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable UUID id,@RequestBody BigDecimal amount){
        try {
            accountService.addBalance(id,amount);
            return ResponseEntity.accepted().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AccountNotActiveException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (InvalidTransferException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable UUID id,@RequestBody BigDecimal amount){
        try {
            accountService.subBalance(id,amount);
            return ResponseEntity.accepted().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AccountNotActiveException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (InvalidTransferException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("{id}/operations")
    public ResponseEntity<?> getOperationsHistory(@PathVariable UUID id,@PageableDefault(page = 0, size = 20) Pageable pageable){
        try {
            Page<AccountOperation> operations = accountService.operationsByAccount(id,pageable);
            return ResponseEntity.ok().body(operations);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
