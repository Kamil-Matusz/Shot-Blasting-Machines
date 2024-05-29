package com.example.api.repository;

import com.example.api.model.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link OrderState} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations and custom queries.
 * </p>
 */
@Repository
public interface OrderStateRepository extends JpaRepository<OrderState, Long> {
    /**
     * Finds an order state by its name.
     *
     * @param name the name of the order state to search for
     * @return the found order state, or {@code null} if no order state was found
     */
    OrderState findByName(String name);
}