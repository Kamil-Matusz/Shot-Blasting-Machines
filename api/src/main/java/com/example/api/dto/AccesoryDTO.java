package com.example.api.dto;

import com.example.api.model.Accesory;
import lombok.Data;

@Data
public class AccesoryDTO {
    private Long id;
    private String name;
    private Double price;

    public static AccesoryDTO convertToDTO(Accesory accesory) {
        AccesoryDTO accesoryDTO = new AccesoryDTO();
        accesoryDTO.setId(accesory.getId());
        accesoryDTO.setName(accesory.getName());
        accesoryDTO.setPrice(accesory.getPrice());
        return accesoryDTO;
    }
}
