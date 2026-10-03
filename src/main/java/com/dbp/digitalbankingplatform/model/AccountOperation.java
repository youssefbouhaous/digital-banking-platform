package com.dbp.digitalbankingplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * AccountOperation
 * Ledger entry for a deposit or withdrawal on a single account
 * (transfers between two accounts are recorded in {@link Transfer}).
 *
 * @author Youssef Bouhaous
 * @since 10/3/26
 */
@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType type;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private BigDecimal balanceAfter;
    private String description;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void createDate(){
        createdAt = LocalDateTime.now();
    }
}
