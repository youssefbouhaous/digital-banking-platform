package com.dbp.digitalbankingplatform.dto.response;


/**
 * TransferByAccountResponce
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
public record TransferByAccountResponse(TransferResponse transfer,String direction) {
}
