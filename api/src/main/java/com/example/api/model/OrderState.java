package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="order_states")
public class OrderState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    public String name;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    public List<Order> orders;
}
