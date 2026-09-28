package com.dbp.digitalbankingplatform.dto.response;

import com.dbp.digitalbankingplatform.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * AccountResponse
 *
 * @author Youssef Bouhaous
 * @since 9/27/26
 */
public record AccountResponse(UUID id, String userName, String firstName, String lastName, BigDecimal balance) {
    public AccountResponse(Account account) {
        this(account.getId(),account.getUsername(), account.getFirstName(), account.getLastName(), account.getBalance());
    }

    public static List<AccountResponse> fromAccounts(List<Account> accounts){
        return accounts.stream().map(AccountResponse::new).toList();
    }
}
