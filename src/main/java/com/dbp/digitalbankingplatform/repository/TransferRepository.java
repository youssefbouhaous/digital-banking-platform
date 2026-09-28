package com.dbp.digitalbankingplatform.repository;

import com.dbp.digitalbankingplatform.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * TransferRepository
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
