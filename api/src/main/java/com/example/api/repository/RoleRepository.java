package com.example.api.repository;

import com.example.api.model.Accesory;
import com.example.api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Role} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations.
 * </p>
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
