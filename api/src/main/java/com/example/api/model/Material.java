package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Represents a material entity.
 * <p>
 * A material is a substance that occurs naturally in the Earth's crust or can be synthesized.
 * </p>
 */
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

    @JsonBackReference
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<NeededMaterials> neededMaterials;
}
