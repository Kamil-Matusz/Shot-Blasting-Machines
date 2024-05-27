package com.example.api.controller;

import com.example.api.model.Role;
import com.example.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing roles.
 * <p>
 * Handles HTTP requests related to roles.
 * </p>
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    /**
     * Constructor of the controller, injecting the role repository.
     *
     * @param roleRepository the role repository
     */
    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves a list of all roles.
     * <p>
     * Mapped to HTTP GET requests for /api/roles.
     * </p>
     *
     * @return ResponseEntity containing a list of Role and HTTP status 200 (OK)
     */
    @GetMapping("")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }
}
