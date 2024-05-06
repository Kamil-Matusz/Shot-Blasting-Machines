package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="accesories")
public class Accesory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="price", nullable = false)
    private Double price;

    @ManyToMany
    @JoinTable(
        name="machines_accesories",
        joinColumns = @JoinColumn(name = "accesory_id"),
        inverseJoinColumns = @JoinColumn(name = "machine_id"))
    private List<Machine> machines;
}