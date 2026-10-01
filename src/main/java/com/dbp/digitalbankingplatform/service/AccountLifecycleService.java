package com.dbp.digitalbankingplatform.service;

import com.dbp.digitalbankingplatform.dto.response.AccountResponse;
import com.dbp.digitalbankingplatform.exception.AccountNotFoundException;
import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.model.AccountState;
import com.dbp.digitalbankingplatform.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * AccountLifecycleService
 *
 * @author Youssef Bouhaous
 * @since 10/1/26
 */

@Service
public class AccountLifecycleService {
    private final AccountRepository accountRepository;

    public AccountLifecycleService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account changeAccountState(UUID id,AccountState state){
        Optional<Account> accountOpt = accountRepository.findById(id);
        if(accountOpt.isEmpty())throw new AccountNotFoundException("Account not found");
        Account account = accountOpt.get();
        account.setState(state);
        return account;
    }

    public Account activateAccount(UUID id){
        return changeAccountState(id,AccountState.ACTIVE);
    }

    public Account closeAccount(UUID id){
        return changeAccountState(id,AccountState.CLOSED);
    }
    public AccountState getLifeCycle(UUID id){
        Optional<Account> accountOpt = accountRepository.findById(id);
        if(accountOpt.isEmpty())throw new AccountNotFoundException("Account not found");
        Account account = accountOpt.get();
        return account.getState();
    }
}
