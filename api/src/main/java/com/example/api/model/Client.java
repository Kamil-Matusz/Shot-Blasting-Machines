package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Represents a client entity.
 * <p>
 * A client is an individual or organization that purchases products or services from a business.
 * </p>
 */
@Entity
@Data
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="phone_number", nullable = false)
    private String phoneNumber;

    @Column(name="address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders;
}
