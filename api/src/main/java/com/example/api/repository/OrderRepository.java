package com.example.api.repository;

import com.example.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link Order} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations.
 * </p>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT SUM(o.price) FROM Order o WHERE o.date >= :startDate GROUP BY DAY(o.date) ORDER BY DAY(o.date)")
    List<Double> findDailySales(@Param("startDate") LocalDateTime startDate);
}