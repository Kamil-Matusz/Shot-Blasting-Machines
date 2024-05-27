package com.example.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ModelCreateDTO {
    private String name;
    private double price;
    private String comments;
    private List<NeededMaterialsAddDTO> neededMaterials;
    public ModelCreateDTO(String name, double price, String comments, List<NeededMaterialsAddDTO> neededMaterials) {
        this.name = name;
        this.price = price;
        this.comments = comments;
        this.neededMaterials = neededMaterials;
    }
}
