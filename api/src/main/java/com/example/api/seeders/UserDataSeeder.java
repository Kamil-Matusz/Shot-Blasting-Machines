package com.example.api.seeders;

import com.example.api.model.Role;
import com.example.api.model.User;
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
@DependsOn("roleDataSeeder")
public class UserDataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Session session = entityManager.unwrap(Session.class);
        List<Role> existingRoles = session.createQuery("FROM Role", Role.class).getResultList();

        if (modelsNotExists()) {
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

            User machineBuilder = new User();
            machineBuilder.setName("Konrad Ryż");
            machineBuilder.setEmail("konradryz@test.com");
            machineBuilder.setPassword("password");
            machineBuilder.setRole(existingRoles.stream().filter(role -> role.getName().equals("Konstruktor Maszyn")).findFirst().orElse(null));
            users.add(machineBuilder);

            User warehouseSupervisor = new User();
            warehouseSupervisor.setName("Jan Papryka");
            warehouseSupervisor.setEmail("janpapryka@test.com");
            warehouseSupervisor.setPassword("password");
            warehouseSupervisor.setRole(existingRoles.stream().filter(role -> role.getName().equals("Nadzorca Magazynu")).findFirst().orElse(null));
            users.add(warehouseSupervisor);

            for (User user : users) {
                entityManager.persist(user);
            }
        }
    }

    private boolean modelsNotExists() {
        Long count = entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class)
                .getSingleResult();
        return count == 0;
    }
}
