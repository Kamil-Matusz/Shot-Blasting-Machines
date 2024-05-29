package com.example.api.dto;

import com.example.api.model.Client;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing client information.
 * <p>
 * ClientDTO is used to transfer data related to clients between different
 * layers of the application. It encapsulates the ID, name, email, phone number,
 * and address of a client.
 * </p>
 */
@Data
public class ClientDTO {
    /** The unique identifier of the client. */
    private Long id;

    /** The name of the client. */
    private String name;

    /** The email of the client. */
    private String email;

    /** The phone number of the client. */
    private String phoneNumber;

    /** The address of the client. */
    private String address;

    /**
     * Converts a Client entity to its corresponding DTO representation.
     *
     * @param client The Client entity to convert.
     * @return The ClientDTO representing the converted Client entity.
     */
    public static ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setPhoneNumber(client.getPhoneNumber());
        return clientDTO;
    }
}
