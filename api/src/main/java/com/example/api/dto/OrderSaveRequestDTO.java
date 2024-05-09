package com.example.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderSaveRequestDTO {
    @NotNull(message = "Cena jest wymagana.")
    @Positive(message = "Cena musi być dodatnia.")
    private Double price;

    @NotNull(message = "Data jest wymagana.")
    private String date;

    @Size(max = 1000, message = "Uwagi nie mogą być dłuższe niż 1000 znaków.")
    private String comments;

    @NotNull(message = "Klient jest wymagany.")
    private Long client;

    @NotNull(message = "Użytkownik jest wymagany.")
    private Long user;

    @NotNull(message = "Model jest wymagany.")
    private Long model;

    @NotNull(message = "Akcesoria są wymagane.")
    private List<Long> accesories;
}
