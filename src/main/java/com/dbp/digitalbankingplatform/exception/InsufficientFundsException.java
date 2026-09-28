package com.dbp.digitalbankingplatform.exception;

/**
 * InsufficientFundsException
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
