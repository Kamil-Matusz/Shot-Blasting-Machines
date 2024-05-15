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
    private List<Integer> neededMaterialIds;
    public ModelCreateDTO(String name, double price, String comments, List<Integer> neededMaterialIds) {
        this.name = name;
        this.price = price;
        this.comments = comments;
        this.neededMaterialIds = neededMaterialIds;
    }
}
