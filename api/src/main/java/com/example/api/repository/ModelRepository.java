package com.example.api.repository;

import com.example.api.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Model findByName(String name);
    Optional<Model> findById(Long id);

}