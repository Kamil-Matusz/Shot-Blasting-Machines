package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    public String name;

    @Column(name="email", nullable = false)
    public String email;

    @Column(name="phone_number", nullable = false)
    public String phoneNumber;

    @Column(name="address", nullable = false)
    public String address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    public List<Order> orders;
}
