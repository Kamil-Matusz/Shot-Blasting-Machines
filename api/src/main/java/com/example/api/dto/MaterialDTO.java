package com.example.api.dto;

import com.example.api.model.Material;
import com.example.api.model.Order;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer amount;

    public static MaterialDTO convertToDTO(Material material) {
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setId(material.getId());
        materialDTO.setName(material.getName());
        materialDTO.setPrice(material.getPrice());
        materialDTO.setAmount(material.getAmount());

        return materialDTO;
    }
}
