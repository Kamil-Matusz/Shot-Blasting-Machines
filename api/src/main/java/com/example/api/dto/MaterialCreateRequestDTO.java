package com.example.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class MaterialCreateRequestDTO {
    @NotNull(message = "Nazwa jest wymagana.")
    private String name;
    @NotNull(message = "Cena jest wymagana.")
    private Double price;
    @NotNull(message = "Ilość jest wymagana.")
    private Integer amount;
}
