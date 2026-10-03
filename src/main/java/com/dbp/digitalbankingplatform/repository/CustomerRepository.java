package com.dbp.digitalbankingplatform.repository;

import com.dbp.digitalbankingplatform.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * CustomerRepository
 *
 * @author Youssef Bouhaous
 * @since 10/3/26
 */
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
}
