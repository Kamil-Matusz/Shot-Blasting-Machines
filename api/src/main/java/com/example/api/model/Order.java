package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;
import java.time.LocalDateTime;

/**
 * Represents an order entity.
 * <p>
 * Order represents a purchase or request made by a client for a machine.
 * </p>
 */
@Entity
@Data
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="price", nullable = false)
    private Double price;

    @Column(name="date", nullable = false)
    private LocalDateTime date;

    @Column(name="comments", nullable = true)
    private String comments;

    @Lob()
    @Column(name="summary", nullable = true, columnDefinition = "BLOB")
    private byte[] summary;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private Client client;

    @ManyToOne
    @JoinColumn(name="state_id", nullable=false)
    private OrderState state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "machine_id", referencedColumnName = "id")
    private Machine machine;
}
