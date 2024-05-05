package com.example.api.seeders;

import com.example.api.model.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn("userDataSeeder")
public class ClientDataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (modelsNotExists()) {
            Session session = entityManager.unwrap(Session.class);

            List<Client> clients = new ArrayList<>();

            Client client1 = new Client();
            client1.setName("Jan Kowalski");
            client1.setEmail("jankowalski@example.com");
            client1.setPhoneNumber("+48123456789");
            client1.setAddress("Konopnickiej 1, Rzeszów, Polska");
            entityManager.persist(client1);

            Client client2 = new Client();
            client2.setName("Paweł Kowal");
            client2.setEmail("pawelkowal@example.com");
            client2.setPhoneNumber("+48987654321");
            client2.setAddress("Cieplownicza 24, Rzeszów, Polska");
            entityManager.persist(client2);

            for (Client client : clients) {
                entityManager.persist(client);
            }
        }
    }

    private boolean modelsNotExists() {
        Long count = entityManager.createQuery("SELECT COUNT(c) FROM Client c", Long.class)
                .getSingleResult();
        return count == 0;
    }
}

