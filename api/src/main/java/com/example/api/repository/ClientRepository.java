package com.example.api.repository;

import com.example.api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Client} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations.
 * </p>
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByName(String name);
}