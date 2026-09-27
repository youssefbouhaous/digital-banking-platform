package com.dbp.digitalbankingplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
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
    private String username;
    private String firstName;
    private String lastName;
    private BigDecimal balance = new BigDecimal(0);
    @Version
    private Long version;
}
