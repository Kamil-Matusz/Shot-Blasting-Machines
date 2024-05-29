package com.example.api.controller;

import com.example.api.dto.UserDTO;
import com.example.api.dto.UserPasswordUpdateDTO;
import com.example.api.dto.UserSaveRequestDTO;
import com.example.api.model.User;
import com.example.api.repository.RoleRepository;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing users.
 * <p>
 * Handles HTTP requests related to users.
 * </p>
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Constructor of the controller, injecting the necessary repositories.
     *
     * @param userRepository the user repository
     * @param roleRepository the role repository
     */
    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves a paginated list of users.
     * <p>
     * Mapped to HTTP GET requests for /api/users.
     * </p>
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of users per page (default is 10)
     * @return a paginated list of UserDTO objects
     */
    @GetMapping("")
    public Page<UserDTO> getUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {

        var pageable = PageRequest.of(page, size);

        var usersPage = userRepository.findAll(pageable);

        List<UserDTO> usersDTOs = usersPage.getContent().stream()
                .map(UserDTO::convertToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(usersDTOs, pageable, usersPage.getTotalElements());
    }

    /**
     * Saves a new user.
     * <p>
     * Mapped to HTTP POST requests for /api/users.
     * </p>
     *
     * @param userSaveRequestDTO the data transfer object containing user information
     * @return ResponseEntity containing the created UserDTO and HTTP status 200 (OK)
     */
    @PostMapping("")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserSaveRequestDTO userSaveRequestDTO) {

        var user = new User();

        var role = roleRepository.findById(userSaveRequestDTO.getRole()).orElse(null);

        if (role == null) {
            return ResponseEntity.notFound().build();
        }

        user.setEmail(userSaveRequestDTO.getEmail());
        user.setName(userSaveRequestDTO.getName());
        user.setRole(role);
        user.setPassword("password");
        userRepository.save(user);

        return new ResponseEntity<>(UserDTO.convertToDTO(user), HttpStatus.OK);
    }

    /**
     * Updates an existing user.
     * <p>
     * Mapped to HTTP PUT requests for /api/users/{id}.
     * </p>
     *
     * @param userSaveRequestDTO the data transfer object containing updated user information
     * @param id the ID of the user to update
     * @return ResponseEntity containing the updated UserDTO and HTTP status 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserSaveRequestDTO userSaveRequestDTO, @PathVariable Long id) {

        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (user.getRole().getId().longValue() != userSaveRequestDTO.getRole().longValue()) {
            var role = roleRepository.findById(userSaveRequestDTO.getRole()).orElse(null);

            if (role == null) {
                return ResponseEntity.notFound().build();
            }

            user.setRole(role);
        }

        user.setEmail(userSaveRequestDTO.getEmail());
        user.setName(userSaveRequestDTO.getName());
        user.setPassword("password");

        userRepository.save(user);

        return new ResponseEntity<>(UserDTO.convertToDTO(user), HttpStatus.OK);
    }

    /**
     * Deletes an existing user.
     * <p>
     * Mapped to HTTP DELETE requests for /api/users/{id}.
     * </p>
     *
     * @param id the ID of the user to delete
     * @return ResponseEntity with HTTP status 204 (No Content) if successful, or 404 (Not Found) if user is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserDTO> updatePassword(@RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO, @PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (user.getPassword().equals(userPasswordUpdateDTO.getOldPassword()) && userPasswordUpdateDTO.getNewPassword().equals(userPasswordUpdateDTO.getRepeatNewPassword())) {
            user.setPassword(userPasswordUpdateDTO.getNewPassword());

            userRepository.save(user);

            return new ResponseEntity<>(UserDTO.convertToDTO(user), HttpStatus.OK);
        }
        else {
            System.out.println(userPasswordUpdateDTO);

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}