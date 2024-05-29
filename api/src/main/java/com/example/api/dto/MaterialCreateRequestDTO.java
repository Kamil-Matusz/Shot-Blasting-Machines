package com.example.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for creating a new material.
 * <p>
 * MaterialCreateRequestDTO is used to transfer data necessary for creating a new material in the system.
 * </p>
 */
@Data
public class MaterialCreateRequestDTO {
    /** The name of the material. */
    @NotNull(message = "Nazwa jest wymagana.")
    private String name;

    /** The price of the material. */
    @NotNull(message = "Cena jest wymagana.")
    private Double price;

    /** The amount of the material. */
    @NotNull(message = "Ilość jest wymagana.")
    private Integer amount;
}
