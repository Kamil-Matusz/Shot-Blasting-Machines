package com.example.api.seeders;

import com.example.api.model.Role;
import com.example.api.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserDataSeeder {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void seedData() {
        Session session = entityManager.unwrap(Session.class);
        List<Role> existingRoles = session.createQuery("FROM Role", Role.class).getResultList();

        List<User> users = new ArrayList<>();

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@blastmachine.com");
        admin.setPassword("password");
        admin.setRole(existingRoles.stream().filter(role -> role.getName().equals("Administrator Systemu")).findFirst().orElse(null));
        users.add(admin);

        User seller = new User();
        seller.setName("Kamil Matusz");
        seller.setEmail("kamilmatusz@test.com");
        seller.setPassword("password");
        seller.setRole(existingRoles.stream().filter(role -> role.getName().equals("Sprzedawca")).findFirst().orElse(null));
        users.add(seller);

        User machineBulider = new User();
        machineBulider.setName("Konrad Ryż");
        machineBulider.setEmail("konradryz@test.com");
        machineBulider.setPassword("password");
        machineBulider.setRole(existingRoles.stream().filter(role -> role.getName().equals("Konstruktor Maszyn")).findFirst().orElse(null));
        users.add(seller);

        User warehouseSupervisor = new User();
        machineBulider.setName("Jan Papryka");
        machineBulider.setEmail("janpapryka@test.com");
        machineBulider.setPassword("password");
        machineBulider.setRole(existingRoles.stream().filter(role -> role.getName().equals("Nadzorca Magazynu")).findFirst().orElse(null));
        users.add(seller);

        for (User user : users) {
            entityManager.persist(user);
        }
    }
}
