package com.example.api.repository;

import com.example.api.model.NeededMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeededMaterialsRepository extends JpaRepository<NeededMaterials, Long> {
}
