package com.example.api.dto;

import com.example.api.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for receiving user save requests.
 * <p>
 * UserSaveRequestDTO is used to transfer user data from the client to the server
 * when saving a new user entity. It contains fields such as name, email, and role ID.
 * </p>
 */
@Data
public class UserSaveRequestDTO {
    /** The name of the user. */
    @NotNull(message = "Nazwa jest wymagana.")
    @Size(min = 5, message = "Nazwa musi mieć conajmniej 5 znaków.")
    @Size(min = 50, message = "Nazwa może mieć conajwyżej 50 znaków.")
    private String name;

    /** The email of the user. */
    @NotNull(message = "Pole email nie może być puste.")
    @Email(message = "Pole email nie jest adresem email.")
    private String email;

    /** The ID of the role associated with the user. */
    @NotNull(message = "Pole role nie może być puste.")
    @Positive(message = "Pole role musi mieć wartość dodatnią.")
    private Long role;
}

