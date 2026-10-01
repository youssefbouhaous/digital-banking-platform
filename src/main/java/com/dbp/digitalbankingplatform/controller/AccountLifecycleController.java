package com.dbp.digitalbankingplatform.controller;

import com.dbp.digitalbankingplatform.exception.AccountNotFoundException;
import com.dbp.digitalbankingplatform.model.AccountState;
import com.dbp.digitalbankingplatform.service.AccountLifecycleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * AccountLifecycleController
 *
 * @author Youssef Bouhaous
 * @since 10/1/26
 */


@RequestMapping("/api/accounts/lifecyle")
@RestController
public class AccountLifecycleController {
    private final AccountLifecycleService accountLifecycleService;

    public AccountLifecycleController(AccountLifecycleService accountLifecycleService) {
        this.accountLifecycleService = accountLifecycleService;
    }

    @GetMapping("{id}/getlifecycle")
    public ResponseEntity<?> getLifeCycleOfAccount(@PathVariable UUID id){
        try {
            AccountState state = accountLifecycleService.getLifeCycle(id);
            return ResponseEntity.ok(state);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("{id}/updatelifecycle")
    public ResponseEntity<?> updateLifeCycleOfAccount(@PathVariable UUID id,@RequestParam AccountState state){
        try {
            accountLifecycleService.changeAccountState(id,state);
            AccountState changedState = accountLifecycleService.getLifeCycle(id);
            return ResponseEntity.ok(changedState);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
