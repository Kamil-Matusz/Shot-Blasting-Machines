package com.example.api.repository;

import com.example.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Order} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations.
 * </p>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}