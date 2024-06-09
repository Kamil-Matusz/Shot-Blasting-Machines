package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Represents an accessory entity.
 * <p>
 * An accessory is an additional item or feature that can be associated with one or more machines.
 * </p>
 */

@Entity
@Data
@Table(name="accessories")
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="price", nullable = false)
    private Double price;

    @ManyToMany
    @JoinTable(
        name="machines_accessories",
        joinColumns = @JoinColumn(name = "accessory_id"),
        inverseJoinColumns = @JoinColumn(name = "machine_id"))
    private List<Machine> machines;
}