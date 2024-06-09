package com.example.api.repository;

import com.example.api.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Material} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations and custom queries.
 * </p>
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    /**
     * Checks if a material is currently in use.
     * <p>
     * This query checks the {@code NeededMaterials} entity to determine if any records are associated with the given material ID.
     * </p>
     *
     * @param materialId the ID of the material to check
     * @return {@code true} if the material is in use, {@code false} otherwise
     */
    @Query("SELECT COUNT(nm) > 0 FROM NeededMaterials nm WHERE nm.material.id = :materialId")
    boolean isMaterialInUse(@Param("materialId") Long materialId);
}
