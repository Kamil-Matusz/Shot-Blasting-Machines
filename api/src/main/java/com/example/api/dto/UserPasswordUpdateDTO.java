package com.example.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPasswordUpdateDTO {
    @NotNull(message = "Stare hasło jest wymagana.")
    private String oldPassword;

    @NotNull(message = "Nowe hasło jest wymagana.")
    @Size(min = 5, message = "Nowe hasło conajmniej 5 znaków.")
    private String newPassword;

    @NotNull(message = "Należy powtórzyć nowe hasło.")
    private String repeatNewPassword;
}
