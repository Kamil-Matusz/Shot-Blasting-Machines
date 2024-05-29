package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a role entity.
 * <p>
 * Role represents a user role within the system, such as "Administrator",
 * "Manager", "User", etc.
 * </p>
 */
@Entity
@Data
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;
}
