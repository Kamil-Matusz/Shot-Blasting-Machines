package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Represents a machine entity.
 * <p>
 * A machine is a device that uses power to apply forces and control movement to perform an intended action.
 * </p>
 */
@Entity
@Data
@Table(name="machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="model_id", nullable=false)
    private Model model;

    @ManyToMany
    @JoinTable(
            name="machines_accessories",
            joinColumns = @JoinColumn(name = "machine_id"),
            inverseJoinColumns = @JoinColumn(name = "accessory_id"))
    private List<Accessory> accessories;
}