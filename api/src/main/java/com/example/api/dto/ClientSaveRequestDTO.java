package com.example.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientSaveRequestDTO {
    @NotNull(message = "Nazwa jest wymagana.")
    @Size(min = 5, message = "Nazwa musi mieć conajmniej 5 znaków.")
    @Size(max = 50, message = "Nazwa może mieć conajwyżej 50 znaków.")
    private String name;

    @NotNull(message = "Pole email nie może być puste.")
    @Email(message = "Pole email nie jest adresem email.")
    private String email;

    @NotNull(message = "Numer telefonu jest wymagany.")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Numer telefonu musi zawierać od 10 do 15 cyfr i może zawierać opcjonalny znak + na początku.")
    private String phoneNumber;

    @NotNull(message = "Adres jest wymagany.")
    @Size(min = 10, max = 100, message = "Adres musi mieć od 10 do 100 znaków.")
    private String address;
}
