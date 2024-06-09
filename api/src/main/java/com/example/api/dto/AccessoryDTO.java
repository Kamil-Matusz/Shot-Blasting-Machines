package com.example.api.dto;

import com.example.api.model.Accessory;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing accessory information.
 * <p>
 * AccesoryDTO is used to transfer data related to accessories between different
 * layers of the application. It encapsulates the ID, name, and price of an accessory.
 * </p>
 */
@Data
public class AccessoryDTO {
    /** The unique identifier of the accessory. */
    private Long id;

    /** The name of the accessory. */
    private String name;

    /** The price of the accessory. */
    private Double price;

    /**
     * Converts an Accesory entity to its corresponding DTO representation.
     *
     * @param accessory The Accesory entity to convert.
     * @return The AccesoryDTO representing the converted Accesory entity.
     */
    public static AccessoryDTO convertToDTO(Accessory accessory) {
        AccessoryDTO accessoryDTO = new AccessoryDTO();
        accessoryDTO.setId(accessory.getId());
        accessoryDTO.setName(accessory.getName());
        accessoryDTO.setPrice(accessory.getPrice());
        return accessoryDTO;
    }
}
