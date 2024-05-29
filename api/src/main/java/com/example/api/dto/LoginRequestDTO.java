package com.example.api.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing a login request.
 * <p>
 * LoginRequestDTO is used to transfer login request data, including
 * email and password, between different layers of the application.
 * </p>
 */
@Data
public class LoginRequestDTO {
    /** The email associated with the login request. */
    private String email;

    /** The password associated with the login request. */
    private String password;
}
