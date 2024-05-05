package com.example.api.repository;

import com.example.api.model.Accesory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesoryRepository extends JpaRepository<Accesory, Long> {
}