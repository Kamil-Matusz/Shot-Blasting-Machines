package com.example.api.repository;

import com.example.api.model.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderState, Long> {
    OrderState findByName(String name);
}