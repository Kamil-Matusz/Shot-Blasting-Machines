package com.example.api.seeders;

import com.example.api.model.Client;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ClientDataSeeder {
    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void seedData() {
        List<Client> clients = new ArrayList<>();

        Client client1 = new Client();
        client1.setName("Jan Kowalski");
        client1.setEmail("jankowalski@example.com");
        client1.setPhoneNumber("+48123456789");
        client1.setAddress("Konopnickiej 1, Rzeszów, Polska");
        clients.add(client1);

        Client client2 = new Client();
        client2.setName("Paweł Kowal");
        client2.setEmail("pawełkowal@example.com");
        client2.setPhoneNumber("+48987654321");
        client2.setAddress("Ciepłownicza 24, Rzeszów, Polska");
        clients.add(client2);

        for (Client client : clients) {
            entityManager.persist(client);
        }
    }
}
