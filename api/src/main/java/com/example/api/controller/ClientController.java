package com.example.api.controller;

import com.example.api.dto.ClientDTO;
import com.example.api.model.Client;
import com.example.api.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setPhoneNumber(client.getPhoneNumber());
        return clientDTO;
    }

    @GetMapping("")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOs = clients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
    }
}
