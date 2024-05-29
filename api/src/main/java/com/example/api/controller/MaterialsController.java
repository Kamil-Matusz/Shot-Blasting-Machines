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

/**
 * REST controller for managing materials.
 * <p>
 * Handles HTTP requests related to materials.
 * </p>
 */
@RestController
@RequestMapping("/api/materials")
public class MaterialsController {
    private final MaterialRepository materialRepository;
    private final NeededMaterialsRepository neededMaterialsRepository;

    /**
     * Constructor of the controller, injecting the material and needed materials repositories.
     *
     * @param materialRepository the material repository
     * @param neededMaterialsRepository the needed materials repository
     */
    @Autowired
    public MaterialsController(MaterialRepository materialRepository,NeededMaterialsRepository neededMaterialsRepository) {
        this.materialRepository = materialRepository;
        this.neededMaterialsRepository = neededMaterialsRepository;
    }

    /**
     * Adds a new material.
     * <p>
     * Mapped to HTTP POST requests for /api/materials.
     * </p>
     *
     * @param materialCreateRequest the material create request data transfer object
     * @return ResponseEntity containing the created MaterialDTO and HTTP status 201 (Created)
     */
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

    /**
     * Retrieves a list of all materials.
     * <p>
     * Mapped to HTTP GET requests for /api/materials.
     * </p>
     *
     * @return ResponseEntity containing a list of MaterialDTO and HTTP status 200 (OK)
     */
    @GetMapping("")
    public ResponseEntity<List<MaterialDTO>> getAllMaterials() {
        List<Material> materials = materialRepository.findAll();

        List<MaterialDTO> materialsDtos = materials.stream()
                .map(MaterialDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(materialsDtos, HttpStatus.OK);
    }

    /**
     * Updates an existing material.
     * <p>
     * Mapped to HTTP PUT requests for /api/materials/{id}.
     * </p>
     *
     * @param id the id of the material to update
     * @param materialUpdateRequest the material update request data transfer object
     * @return ResponseEntity containing the updated MaterialDTO and HTTP status 200 (OK), or HTTP status 404 (Not Found) if the material does not exist
     */
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

    /**
     * Deletes an existing material.
     * <p>
     * Mapped to HTTP DELETE requests for /api/materials/{id}.
     * </p>
     *
     * @param id the id of the material to delete
     * @return ResponseEntity with HTTP status 204 (No Content) if the deletion was successful, or HTTP status 404 (Not Found) if the material does not exist, or HTTP status 400 (Bad Request) if the material is in use
     */
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

    /**
     * Increases the amount of an existing material.
     * <p>
     * Mapped to HTTP PATCH requests for /api/materials/{id}/increase-amount.
     * </p>
     *
     * @param id the id of the material to update
     * @param amount the amount change request data transfer object
     * @return ResponseEntity containing the updated MaterialDTO and HTTP status 200 (OK), or HTTP status 404 (Not Found) if the material does not exist
     */
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

    /**
     * Decreases the amount of an existing material.
     * <p>
     * Mapped to HTTP PATCH requests for /api/materials/{id}/decrease-amount.
     * </p>
     *
     * @param id the id of the material to update
     * @param amount the amount change request data transfer object
     * @return ResponseEntity containing the updated MaterialDTO and HTTP status 200 (OK), or HTTP status 400 (Bad Request) if the current amount is insufficient, or HTTP status 404 (Not Found) if the material does not exist
     */
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
