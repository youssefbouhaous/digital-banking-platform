package com.dbp.digitalbankingplatform.repository;

import com.dbp.digitalbankingplatform.model.AccountStateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dbp.digitalbankingplatform.model.Account;

import java.util.List;
import java.util.UUID;

/**
 * AccountStateHistoryRepository
 *
 * @author Youssef Bouhaous
 * @since 10/3/26
 */
public interface AccountStateHistoryRepository extends JpaRepository<AccountStateHistory, UUID> {
    List<AccountStateHistory> findAllByAccountOrderByChangedAtDesc(Account account);
}
