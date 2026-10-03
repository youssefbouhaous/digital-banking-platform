package com.dbp.digitalbankingplatform.repository;

import com.dbp.digitalbankingplatform.model.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dbp.digitalbankingplatform.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * AccountOperationRepository
 *
 * @author Youssef Bouhaous
 * @since 10/3/26
 */
public interface AccountOperationRepository extends JpaRepository<AccountOperation, UUID> {
    Page<AccountOperation> findAllByAccountOrderByCreatedAtDesc(Account account, Pageable pageable);
}
