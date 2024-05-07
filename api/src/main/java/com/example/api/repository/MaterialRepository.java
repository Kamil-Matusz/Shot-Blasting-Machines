package com.example.api.repository;

import com.example.api.model.Material;
import com.example.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    @Query("SELECT COUNT(nm) > 0 FROM NeededMaterials nm WHERE nm.material.id = :materialId")
    boolean isMaterialInUse(@Param("materialId") Long materialId);
}
