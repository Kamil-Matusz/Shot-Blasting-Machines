package com.example.api.dto;

import lombok.Data;

import java.util.Optional;

/**
 * Data Transfer Object (DTO) for representing changes in the amount of a material.
 * <p>
 * MaterialAmountChangeDTO is used to transfer the new amount of a material between different layers of the application.
 * </p>
 */
@Data
public class MaterialAmountChangeDTO {
    /** The new amount of the material. */
    private Integer amount;
}
