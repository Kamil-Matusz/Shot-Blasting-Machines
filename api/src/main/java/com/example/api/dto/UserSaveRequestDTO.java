package com.example.api.dto;

import com.example.api.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSaveRequestDTO {
    @NotNull(message = "Nazwa jest wymagana.")
    @Size(min = 5, message = "Nazwa musi mieć conajmniej 5 znaków.")
    @Size(min = 50, message = "Nazwa może mieć conajwyżej 50 znaków.")
    private String name;

    @NotNull(message = "Pole email nie może być puste.")
    @Email(message = "Pole email nie jest adresem email.")
    private String email;

    @NotNull(message = "Pole role nie może być puste.")
    @Positive(message = "Pole role musi mieć wartość dodatnią.")
    private Long role;
}

