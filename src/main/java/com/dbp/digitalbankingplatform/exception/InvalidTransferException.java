package com.dbp.digitalbankingplatform.exception;

/**
 * InvalidTransferException
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
public class InvalidTransferException extends RuntimeException {
    public InvalidTransferException(String message) {
        super(message);
    }
}
