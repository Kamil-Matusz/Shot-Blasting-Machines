package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an order state entity.
 * <p>
 * OrderState represents the different states that an order can have,
 * such as "Pending", "Processing", "Shipped", etc.
 * </p>
 */
@Entity
@Data
@Table(name="order_states")
public class OrderState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private List<Order> orders;
}
