package com.example.api.service;

import com.example.api.config.CustomUserDetailsService;
import com.example.api.config.JWTUtils;
import com.example.api.dto.CustomUserDetails;
import com.example.api.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for handling login operations.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTUtils jwtUtils;

    /**
     * Authenticates the user and generates a JWT token if authentication is successful.
     *
     * @param loginRequestDTO the data transfer object containing login information (email and password)
     * @return ResponseEntity containing the JWT token if successful, or an error message if authentication fails
     */
    public ResponseEntity<String> login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        final CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequestDTO.getEmail());
        if (userDetails != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));
        }
        return ResponseEntity.status(400).body("Failed To Login!");
    }
}
