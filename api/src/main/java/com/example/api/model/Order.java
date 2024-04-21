package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="price", nullable = false)
    public Double price;

    @Column(name="date", nullable = false)
    public LocalDateTime date;

    @Column(name="comments", nullable = false)
    public String comments;
}
