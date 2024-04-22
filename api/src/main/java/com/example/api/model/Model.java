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
    public String name;

    @Column(name="price", nullable = false)
    public Double price;

    @Column(name="comments", nullable = false)
    public String comments;
}
