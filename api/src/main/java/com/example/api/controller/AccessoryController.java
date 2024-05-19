package com.example.api.controller;

import com.example.api.dto.AccessoryDTO;
import com.example.api.model.Accessory;
import com.example.api.repository.AccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accessories")
public class AccessoryController {

    private final AccessoryRepository accessoryRepository;

    @Autowired
    public AccessoryController(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }


    @GetMapping("")
    public ResponseEntity<List<AccessoryDTO>> getAllAccesories() {
        List<Accessory> accessories = accessoryRepository.findAll();

        List<AccessoryDTO> accessoryDTOS = accessories.stream()
                .map(AccessoryDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(accessoryDTOS, HttpStatus.OK);
    }
}
