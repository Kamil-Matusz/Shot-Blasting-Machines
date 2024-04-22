package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="needed_materials")
public class NeededMaterials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id", nullable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id", nullable = false)
    private Material material;

    @Column(name="amount", nullable = false)
    public Integer amount;
}
