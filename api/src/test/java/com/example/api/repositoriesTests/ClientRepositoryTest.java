package com.example.api.repositoriesTests;

import com.example.api.model.Client;
import com.example.api.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void findAllClients() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).isNotNull();
        assertThat(clients).hasSizeGreaterThan(0);
    }

    @Test
    public void findClientById() {
        Long clientId = 1L;
        Client client = clientRepository.findById(clientId).orElse(null);
        assertThat(client).isNotNull();
    }
}
