package com.example.api.controller;

import com.example.api.model.Machine;
import com.example.api.model.Model;
import com.example.api.repository.MachineRepository;
import com.example.api.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Retrieves a list of all machines.
 * <p>
 * Mapped to HTTP GET requests for /api/machines.
 * </p>
 *
 * @return ResponseEntity containing a list of Machine and HTTP status 200 (OK)
 */
@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineRepository machineRepository;

    @Autowired
    public MachineController(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }


}
