package com.dbp.digitalbankingplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Transfer
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private Account sender;
    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void createDate(){
        createdAt = LocalDateTime.now();
    }
}

