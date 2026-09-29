package com.dbp.digitalbankingplatform.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * TransferRequest
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
public record TransferRequest(@NotNull UUID senderId, @NotNull UUID receiverId,@NotNull @Positive BigDecimal amount) {
}
