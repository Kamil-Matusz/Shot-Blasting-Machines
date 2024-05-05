package com.example.api.controller;

import com.example.api.dto.AccesoryDTO;
import com.example.api.model.Accesory;
import com.example.api.repository.AccesoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accesories")
public class AccesoryController {

    private final AccesoryRepository accesoryRepository;

    @Autowired
    public AccesoryController(AccesoryRepository accesoryRepository) {
        this.accesoryRepository = accesoryRepository;
    }


    @GetMapping("")
    public ResponseEntity<List<AccesoryDTO>> getAllAccesories() {
        List<Accesory> accesories = accesoryRepository.findAll();

        List<AccesoryDTO> accesoryDTOS = accesories.stream()
                .map(AccesoryDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(accesoryDTOS, HttpStatus.OK);
    }
}
