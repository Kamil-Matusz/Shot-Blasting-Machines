package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Represents a user entity.
 * <p>
 * User represents an individual user within the system, with associated information
 * such as name, email, password, and role.
 * </p>
 */
@Entity
@Data
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
}
