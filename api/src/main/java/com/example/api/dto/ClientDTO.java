package com.example.api.dto;

import com.example.api.model.Client;
import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

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
