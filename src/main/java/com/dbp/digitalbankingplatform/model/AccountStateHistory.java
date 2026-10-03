package com.dbp.digitalbankingplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * AccountStateHistory
 * Audit trail of every lifecycle change of an account (e.g. FROZEN -> ACTIVE).
 *
 * @author Youssef Bouhaous
 * @since 10/3/26
 */
@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class AccountStateHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @Enumerated(EnumType.STRING)
    private AccountState previousState;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountState newState;
    private String reason;
    @Column(nullable = false, updatable = false)
    private LocalDateTime changedAt;

    @PrePersist
    public void changeDate(){
        changedAt = LocalDateTime.now();
    }
}
