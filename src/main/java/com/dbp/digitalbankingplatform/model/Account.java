package com.dbp.digitalbankingplatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Account
 *
 * @author Youssef Bouhaous
 * @since 9/25/26
 */
@AllArgsConstructor @NoArgsConstructor @Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @PositiveOrZero
    private BigDecimal balance = new BigDecimal(0);
    @Version
    private Long version;
    private AccountState state = AccountState.FROZEN;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
