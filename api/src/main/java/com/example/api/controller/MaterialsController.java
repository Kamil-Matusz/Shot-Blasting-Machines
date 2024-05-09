package com.example.api.controller;

import com.example.api.dto.*;
import com.example.api.model.*;
import com.example.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.api.dto.OrderDTO.convertToDTO;

@RestController
@RequestMapping("/api/materials")
public class MaterialsController {
    private final MaterialRepository materialRepository;
    private final NeededMaterialsRepository neededMaterialsRepository;

    @Autowired
    public MaterialsController(MaterialRepository materialRepository,NeededMaterialsRepository neededMaterialsRepository) {
        this.materialRepository = materialRepository;
        this.neededMaterialsRepository = neededMaterialsRepository;
    }
    @PostMapping("")
    public ResponseEntity<MaterialDTO> addOrder(@RequestBody MaterialCreateRequestDTO materialCreateRequest) {
        Material material = new Material();

        material.setName(materialCreateRequest.getName());
        material.setAmount(materialCreateRequest.getAmount());
        material.setPrice(materialCreateRequest.getPrice());

        materialRepository.save(material);
        MaterialDTO materialDTO = MaterialDTO.convertToDTO(material);
        return new ResponseEntity<>(materialDTO, HttpStatus.CREATED);
    }
    @GetMapping("")
    public ResponseEntity<List<MaterialDTO>> getAllMaterials() {
        List<Material> materials = materialRepository.findAll();

        List<MaterialDTO> materialsDtos = materials.stream()
                .map(MaterialDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(materialsDtos, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> updateMaterial(@PathVariable Long id,
                                                      @RequestBody MaterialCreateRequestDTO materialUpdateRequest) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isPresent()) {
            Material material = optionalMaterial.get();
            material.setName(materialUpdateRequest.getName());
            material.setAmount(materialUpdateRequest.getAmount());
            material.setPrice(materialUpdateRequest.getPrice());
            materialRepository.save(material);
            MaterialDTO materialDTO = MaterialDTO.convertToDTO(material);
            return new ResponseEntity<>(materialDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaterial(@PathVariable Long id) {
        // Check if the material exists
        Material material = materialRepository.findById(id)
                .orElse(null);

        if (material == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if the material is associated with any needed materials
        boolean isMaterialInUse = materialRepository.isMaterialInUse(id);

        if (isMaterialInUse) {
            return ResponseEntity.badRequest().body("Nie można usunąć materiału ponieważ jest on wykorzysytwany");
        }

        // If not in use, proceed with deletion
        materialRepository.delete(material);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/increase-amount")
    public ResponseEntity<MaterialDTO> increaseMaterialAmount(@PathVariable Long id,
                                                              @RequestBody MaterialAmountChangeDTO amount) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isPresent()) {
            Material material = optionalMaterial.get();
            material.setAmount(material.getAmount() + amount.getAmount());
            materialRepository.save(material);
            MaterialDTO materialDTO = MaterialDTO.convertToDTO(material);
            return new ResponseEntity<>(materialDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/decrease-amount")
    public ResponseEntity<MaterialDTO> decreaseMaterialAmount(@PathVariable Long id,
                                                              @RequestBody MaterialAmountChangeDTO amount) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isPresent()) {
            Material material = optionalMaterial.get();
            int currentAmount = material.getAmount();
            if (currentAmount >= amount.getAmount()) {
                material.setAmount(currentAmount - amount.getAmount());
                materialRepository.save(material);
                MaterialDTO materialDTO = MaterialDTO.convertToDTO(material);
                return new ResponseEntity(materialDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity("Nie wystarczająca ilość materiału", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
