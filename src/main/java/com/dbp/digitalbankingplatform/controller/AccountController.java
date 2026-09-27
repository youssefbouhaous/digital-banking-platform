package com.dbp.digitalbankingplatform.controller;

import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * AccountController
 *
 * @author Youssef Bouhaous
 * @since 9/25/26
 */
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Account account){
        Account accountSaved = accountService.create(account);
        return ResponseEntity.ok(accountSaved);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("balance/{uuid}")
    public ResponseEntity<?> getBalance(@PathVariable UUID uuid){
        return ResponseEntity.ok(accountService.checkBalance(uuid));
    }

}
