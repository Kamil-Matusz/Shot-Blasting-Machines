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

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    public User user;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    public Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "machine_id", referencedColumnName = "id")
    private Machine machine;
}
