package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="price", nullable = false)
    private Double price;

    @Column(name="amount", nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<NeededMaterials> neededMaterials;
}