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

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineRepository machineRepository;

    @Autowired
    public MachineController(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    // TODO: Dodac tworzenie egzemplarza maszyny przed stworzeniem zamowienia
    @PostMapping("")
    public ResponseEntity<Machine> addMachine(@RequestBody Machine machine) {
        return new ResponseEntity<>(machineRepository.save(machine), HttpStatus.CREATED);
    }
}
