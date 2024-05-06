package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="price", nullable = false)
    private Double price;

    @Column(name="comments", nullable = false)
    private String comments;
}
