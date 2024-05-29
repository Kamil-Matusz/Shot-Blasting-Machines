package com.example.api.repository;

import com.example.api.model.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Accesory} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations.
 * </p>
 */
@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
}