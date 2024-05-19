package com.example.api.repository;

import com.example.api.model.Machine;
import com.example.api.model.Model;
import com.example.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    Optional<Machine> findById(Long id);

    List<Machine> findByModel(Model model);
}