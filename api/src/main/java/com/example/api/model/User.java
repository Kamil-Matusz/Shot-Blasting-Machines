package com.example.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    public String name;

    @Column(name="email", nullable = false)
    public String email;

    @Column(name="password", nullable = false)
    public String password;

    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    public Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Order> orders;
}
