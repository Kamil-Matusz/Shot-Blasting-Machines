package com.example.api.dto;

import com.example.api.model.Accessory;
import lombok.Data;

@Data
public class AccessoryDTO {
    private Long id;
    private String name;
    private Double price;

    public static AccessoryDTO convertToDTO(Accessory accessory) {
        AccessoryDTO accessoryDTO = new AccessoryDTO();
        accessoryDTO.setId(accessory.getId());
        accessoryDTO.setName(accessory.getName());
        accessoryDTO.setPrice(accessory.getPrice());
        return accessoryDTO;
    }
}
