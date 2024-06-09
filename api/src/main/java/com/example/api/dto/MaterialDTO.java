package com.example.api.dto;

import com.example.api.model.Material;
import com.example.api.model.Order;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for representing material data.
 * <p>
 * MaterialDTO is used to transfer material data between different layers of the application.
 * It contains fields such as id, name, price, and amount.
 * </p>
 */
@Data
public class MaterialDTO {
    /** The unique identifier of the material. */
    private Long id;

    /** The name of the material. */
    private String name;

    /** The price of the material. */
    private Double price;

    /** The amount of the material. */
    private Integer amount;

    /**
     * Converts a Material entity to a MaterialDTO.
     *
     * @param material The Material entity to be converted.
     * @return The MaterialDTO representation of the Material entity.
     */
    public static MaterialDTO convertToDTO(Material material) {
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setId(material.getId());
        materialDTO.setName(material.getName());
        materialDTO.setPrice(material.getPrice());
        materialDTO.setAmount(material.getAmount());

        return materialDTO;
    }
}
