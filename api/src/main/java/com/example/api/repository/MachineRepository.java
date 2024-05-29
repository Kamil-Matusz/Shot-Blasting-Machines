package com.example.api.repository;

import com.example.api.model.Machine;
import com.example.api.model.Model;
import com.example.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Machine} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations and custom queries.
 * </p>
 */
@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    /**
     * Finds a machine by its ID.
     *
     * @param id the ID of the machine to search for
     * @return an {@link Optional} containing the found machine, or {@link Optional#empty()} if no machine was found
     */
    Optional<Machine> findById(Long id);

    List<Machine> findByModel(Model model);
}