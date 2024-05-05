package com.example.api.dto;

import com.example.api.model.Role;
import com.example.api.model.User;
import lombok.Data;


@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;

    public static UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
