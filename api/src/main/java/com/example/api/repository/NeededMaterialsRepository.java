package com.example.api.repository;

import com.example.api.model.NeededMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link NeededMaterials} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations.
 * </p>
 */
@Repository
public interface NeededMaterialsRepository extends JpaRepository<NeededMaterials, Long> {
}
