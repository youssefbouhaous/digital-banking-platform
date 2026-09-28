package com.dbp.digitalbankingplatform.exception;

/**
 * AccountNotFoundException
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
