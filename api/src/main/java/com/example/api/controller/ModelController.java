package com.example.api.controller;

import com.example.api.dto.ModelCreateDTO;
import com.example.api.dto.NeededMaterialsAddDTO;
import com.example.api.model.Machine;
import com.example.api.model.Material;
import com.example.api.model.Model;
import com.example.api.model.NeededMaterials;
import com.example.api.repository.MachineRepository;
import com.example.api.repository.MaterialRepository;
import com.example.api.repository.ModelRepository;
import com.example.api.repository.NeededMaterialsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/models")
public class ModelController {

    private final ModelRepository modelRepository;
    private final MaterialRepository materialRepository;
    private final NeededMaterialsRepository neededMaterialsRepository;

    private final MachineRepository machineRepository;

    @Autowired
    public ModelController(ModelRepository modelRepository, MaterialRepository materialRepository, NeededMaterialsRepository neededMaterialsRepository, MachineRepository machineRepository) {
        this.modelRepository = modelRepository;
        this.materialRepository = materialRepository;
        this.neededMaterialsRepository = neededMaterialsRepository;
        this.machineRepository = machineRepository;
    }

    @GetMapping("")
    public ResponseEntity<Page<Model>> getAllModels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Model> models = modelRepository.findAll(pageable);
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Model> createModelWithNeededMaterials(@RequestBody ModelCreateDTO modelCreateDTO) {
        try {
            // Create a new Model object and set its properties based on the input DTO
            Model model = new Model();
            model.setName(modelCreateDTO.getName());
            model.setPrice(modelCreateDTO.getPrice());
            model.setComments(modelCreateDTO.getComments());

            // Save the model
            Model savedModel = modelRepository.save(model);

            // Handle needed materials
            List<NeededMaterials> neededMaterialsList = new ArrayList<>();
            for (NeededMaterialsAddDTO neededMaterialsAddDTO : modelCreateDTO.getNeededMaterials()) {
                // Retrieve material from repository based on ID
                long materialId = neededMaterialsAddDTO.getId();
                int amount = neededMaterialsAddDTO.getAmount();
                Material material = materialRepository.findById((long)materialId).orElse(null);
                if (material != null) {
                    NeededMaterials neededMaterials = new NeededMaterials();
                    neededMaterials.setModel(savedModel);
                    neededMaterials.setMaterial(material);
                    neededMaterials.setAmount(amount);
                    neededMaterialsList.add(neededMaterials);
                } else {
                    // Handle case where material with the given ID is not found
                    // You may throw an exception or handle it based on your application's logic
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            }
            // Save the list of needed materials
            neededMaterialsRepository.saveAll(neededMaterialsList); // Save all needed materials

            // Return the saved model
            return new ResponseEntity<>(savedModel, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModel(@PathVariable Long id) {
        try {
            // Check if the model exists
            Optional<Model> modelOptional = modelRepository.findById(id);
            if (modelOptional.isPresent()) {
                Model model = modelOptional.get();

                // Check if there are any dependent machines
                List<Machine> machines = machineRepository.findByModel(model);
                if (!machines.isEmpty()) {
                    return new ResponseEntity<>("Nie można usunąć modelu. Jest przypisany do istniejącej maszyny.", HttpStatus.BAD_REQUEST);
                }

                // Delete the model
                modelRepository.delete(model);

                return new ResponseEntity<>("Model deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Model not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete model", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Model> updateModel(@PathVariable Long id, @RequestBody ModelCreateDTO modelCreateDTO) {
        try {
            // Check if the model exists
            Optional<Model> modelOptional = modelRepository.findById(id);
            if (modelOptional.isPresent()) {
                Model model = modelOptional.get();

                // Update model properties
                model.setName(modelCreateDTO.getName());
                model.setPrice(modelCreateDTO.getPrice());
                model.setComments(modelCreateDTO.getComments());

                // Update needed materials
                List<NeededMaterials> neededMaterialsList = new ArrayList<>();
                for (NeededMaterialsAddDTO neededMaterialsAddDTO : modelCreateDTO.getNeededMaterials()) {
                    long materialId = neededMaterialsAddDTO.getId();
                    int amount = neededMaterialsAddDTO.getAmount();
                    Material material = materialRepository.findById(materialId).orElse(null);
                    if (material != null) {
                        // Check if needed material already exists
                        Optional<NeededMaterials> existingNeededMaterial = model.getNeededMaterials().stream()
                                .filter(needed -> needed.getMaterial().getId().equals(materialId))
                                .findFirst();
                        if (existingNeededMaterial.isPresent()) {
                            // Update existing needed material
                            NeededMaterials neededMaterials = existingNeededMaterial.get();
                            neededMaterials.setAmount(amount);
                            neededMaterialsList.add(neededMaterials);
                        } else {
                            // Add new needed material
                            NeededMaterials neededMaterials = new NeededMaterials();
                            neededMaterials.setModel(model);
                            neededMaterials.setMaterial(material);
                            neededMaterials.setAmount(amount);
                            neededMaterialsList.add(neededMaterials);
                        }
                    } else {
                        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                    }
                }
                // Remove any needed materials that are not in the updated list
                model.getNeededMaterials().removeIf(needed -> !neededMaterialsList.contains(needed));

                // Save the updated list of needed materials
                neededMaterialsRepository.saveAll(neededMaterialsList);

                // Save the updated model
                Model updatedModel = modelRepository.save(model);

                return new ResponseEntity<>(updatedModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
