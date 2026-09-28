package com.dbp.digitalbankingplatform.controller;

import com.dbp.digitalbankingplatform.dto.request.CreateAccountRequest;
import com.dbp.digitalbankingplatform.dto.response.AccountResponse;
import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.service.AccountService;
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
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest createAccountRequest){
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

}
