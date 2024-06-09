package com.example.api.repository;

import com.example.api.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Model} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations and custom queries.
 * </p>
 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    /**
     * Finds a model by its name.
     *
     * @param name the name of the model to search for
     * @return the found model, or {@code null} if no model was found
     */
    Model findByName(String name);

    /**
     * Finds a model by its ID.
     *
     * @param id the ID of the model to search for
     * @return an {@link Optional} containing the found model, or {@link Optional#empty()} if no model was found
     */
    Optional<Model> findById(Long id);

}