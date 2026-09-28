package com.dbp.digitalbankingplatform.dto.response;

import com.dbp.digitalbankingplatform.model.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * TransferResponse
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
/*
Transfer ID
Sender and receiver IDs
Amount
Creation time
 */
public record TransferResponse(UUID idTransfer, UUID idSender, UUID idReceiver, BigDecimal amount, LocalDateTime createdAt) {
    public TransferResponse(Transfer transfer) {
        this(transfer.getId(), transfer.getSender().getId(), transfer.getReceiver().getId(), transfer.getAmount(), transfer.getCreatedAt());
    }
}
