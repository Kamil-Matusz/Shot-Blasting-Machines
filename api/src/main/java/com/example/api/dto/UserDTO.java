package com.example.api.dto;

import com.example.api.model.Role;
import com.example.api.model.User;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing user data.
 * <p>
 * UserDTO is used to transfer user data between different layers of the application.
 * It contains fields such as id, name, email, and role.
 * </p>
 */
@Data
public class UserDTO {

    /** The unique identifier of the user. */
    private Long id;

    /** The name of the user. */
    private String name;

    /** The email address of the user. */
    private String email;

    /** The role associated with the user. */
    private Role role;
//    private String password;

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user The User entity to be converted.
     * @return The UserDTO representation of the User entity.
     */
    public static UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
//        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
