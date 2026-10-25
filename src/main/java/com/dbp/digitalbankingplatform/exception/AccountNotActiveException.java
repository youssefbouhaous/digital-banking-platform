package com.dbp.digitalbankingplatform.exception;

/**
 * AccountNotActiveException
 *
 * @author Youssef Bouhaous
 * @since 10/26/26
 */
public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(String message) {
        super(message);
    }
}
