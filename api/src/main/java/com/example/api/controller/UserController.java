package com.example.api.controller;

import com.example.api.dto.UserDTO;
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

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}