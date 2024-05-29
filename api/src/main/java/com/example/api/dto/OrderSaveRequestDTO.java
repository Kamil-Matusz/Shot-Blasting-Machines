package com.example.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for saving order information.
 * <p>
 * OrderSaveRequestDTO is used to transfer data necessary for saving an order in the system.
 * </p>
 */
@Data
public class OrderSaveRequestDTO {
    /** The price of the order. */
    @NotNull(message = "Cena jest wymagana.")
    @Positive(message = "Cena musi być dodatnia.")
    private Double price;

    /** The date of the order. */
    @NotNull(message = "Data jest wymagana.")
    private String date;

    /** Comments or notes related to the order. */
    @Size(max = 1000, message = "Uwagi nie mogą być dłuższe niż 1000 znaków.")
    private String comments;

    /** The ID of the client associated with the order. */
    @NotNull(message = "Klient jest wymagany.")
    private Long client;

    /** The ID of the user who placed the order. */
    @NotNull(message = "Użytkownik jest wymagany.")
    private Long user;

    /** The ID of the model associated with the order. */
    @NotNull(message = "Model jest wymagany.")
    private Long model;

    /** The IDs of the accessories associated with the order. */
    @NotNull(message = "Akcesoria są wymagane.")
    private List<Long> accessories;
}
