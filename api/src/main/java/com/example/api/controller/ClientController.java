package com.example.api.controller;

import com.example.api.dto.ClientDTO;
import com.example.api.dto.ClientSaveRequestDTO;
import com.example.api.model.Client;
import com.example.api.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOs = clients.stream()
                .map(ClientDTO::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("id") Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            ClientDTO clientDTO = ClientDTO.convertToDTO(optionalClient.get());
            return new ResponseEntity<>(clientDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientSaveRequestDTO clientSaveRequestDTO) {
        Client client = new Client();
        client.setName(clientSaveRequestDTO.getName());
        client.setEmail(clientSaveRequestDTO.getEmail());
        client.setPhoneNumber(clientSaveRequestDTO.getPhoneNumber());
        client.setAddress(clientSaveRequestDTO.getAddress());
        client = clientRepository.save(client);
        ClientDTO savedClientDTO = ClientDTO.convertToDTO(client);
        return new ResponseEntity<>(savedClientDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("id") Long id, @Valid @RequestBody ClientSaveRequestDTO clientSaveRequestDTO) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setName(clientSaveRequestDTO.getName());
            client.setEmail(clientSaveRequestDTO.getEmail());
            client.setPhoneNumber(clientSaveRequestDTO.getPhoneNumber());
            client.setAddress(clientSaveRequestDTO.getAddress());
            client = clientRepository.save(client);
            ClientDTO updatedClientDTO = ClientDTO.convertToDTO(client);
            return new ResponseEntity<>(updatedClientDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            clientRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
