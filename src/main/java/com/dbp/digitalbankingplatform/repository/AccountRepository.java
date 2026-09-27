package com.dbp.digitalbankingplatform.repository;

import com.dbp.digitalbankingplatform.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * AccountRepository
 *
 * @author Youssef Bouhaous
 * @since 9/25/26
 */

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
