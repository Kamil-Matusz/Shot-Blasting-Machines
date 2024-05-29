package com.example.api.controller;

import com.example.api.dto.ClientDTO;
import com.example.api.dto.ClientSaveRequestDTO;
import com.example.api.dto.UserDTO;
import com.example.api.model.Client;
import com.example.api.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing clients.
 * <p>
 * Handles HTTP requests related to clients.
 * </p>
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    /**
     * Constructor of the controller, injecting the client repository.
     *
     * @param clientRepository the client repository
     */
    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Retrieves a list of all clients.
     * <p>
     * Mapped to HTTP GET requests for /api/clients.
     * </p>
     *
     * @return ResponseEntity containing a list of ClientDTO and HTTP status 200 (OK)
     */
    @GetMapping("")
    public Page<ClientDTO> getAllClients(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        var pageable = PageRequest.of(page, size);
        var clientPage = clientRepository.findAll(pageable);


        List<ClientDTO> clientDTOs = clientPage.stream()
                .map(ClientDTO::convertToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(clientDTOs, pageable, clientPage.getTotalElements());
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
