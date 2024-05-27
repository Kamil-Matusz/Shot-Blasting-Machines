package com.example.api.controller;

import com.example.api.dto.LoginRequestDTO;
import com.example.api.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for managing login operations.
 * <p>
 * Handles HTTP requests related to user authentication.
 * </p>
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * Authenticates a user and returns a JWT.
     * <p>
     * Mapped to HTTP POST requests for /api/login.
     * </p>
     *
     * @param loginRequestDTO the login request data transfer object containing user credentials
     * @return ResponseEntity containing a map with the JWT and HTTP status 200 (OK)
     */
    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String jwtResponse = String.valueOf(loginService.login(loginRequestDTO));
        String[] parts = jwtResponse.split(",");
        String jwt = parts[1].trim();
        Map<String, String> response = new HashMap<>();
        response.put("jwt", jwt);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}