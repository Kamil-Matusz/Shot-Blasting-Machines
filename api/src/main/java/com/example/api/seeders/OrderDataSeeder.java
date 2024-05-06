package com.example.api.seeders;

import com.example.api.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn({"roleDataSeeder", "userDataSeeder", "materialDataSeeder", "orderStateDataSeeder", "modelDataSeeder", "machineDataSeeder"})
public class OrderDataSeeder implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        Session session = entityManager.unwrap(Session.class);
        List<User> existingUsers = session.createQuery("FROM User ", User.class).getResultList();
        List<Client> existingClients = session.createQuery("FROM Client ", Client.class).getResultList();
        List<OrderState> existingStates = session.createQuery("FROM OrderState ", OrderState.class).getResultList();
        List<Machine> existingMachines = session.createQuery("FROM Machine ", Machine.class).getResultList();

        if (modelsNotExists()) {

            List<Order> orders = new ArrayList<>();

            Order order1 = new Order();
            order1.setPrice(30000.00);
            order1.setDate(LocalDateTime.now());
            order1.setComments("Zamówienie zakończone");
            order1.setUser(existingUsers.stream().filter(user -> user.getName().equals("Admin")).findFirst().orElse(null));
            order1.setClient(existingClients.stream().filter(client -> client.getName().equals("Jan Kowalski")).findFirst().orElse(null));
            order1.setState(existingStates.stream().filter(state -> state.getName().equals("Wysłane")).findFirst().orElse(null));
            order1.setMachine(existingMachines.stream().filter(machine -> machine.getId().equals(1L)).findFirst().orElse(null));
            orders.add(order1);

            for (Order order : orders) {
                entityManager.persist(order);
            }
        }
    }

    private boolean modelsNotExists() {
        Long count = entityManager.createQuery("SELECT COUNT(o) FROM Order o", Long.class)
                .getSingleResult();
        return count == 0;
    }
}
