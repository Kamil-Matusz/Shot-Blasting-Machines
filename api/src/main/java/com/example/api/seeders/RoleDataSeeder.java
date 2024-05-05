package com.example.api.seeders;

import com.example.api.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RoleDataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (modelsNotExists()) {

            Role admin = new Role();
            admin.setName("Administrator Systemu");
            entityManager.persist(admin);

            Role machineBuilder = new Role();
            machineBuilder.setName("Konstruktor Maszyn");
            entityManager.persist(machineBuilder);

            Role warehouseSupervisor = new Role();
            warehouseSupervisor.setName("Nadzorca Magazynu");
            entityManager.persist(warehouseSupervisor);

            Role seller = new Role();
            seller.setName("Sprzedawca");
            entityManager.persist(seller);

            Role machineConstructor = new Role();
            machineConstructor.setName("Pracownik Produkcji");
            entityManager.persist(machineConstructor);
        }
    }

    private boolean modelsNotExists() {
        Long count = entityManager.createQuery("SELECT COUNT(r) FROM Role r", Long.class)
                .getSingleResult();
        return count == 0;
    }
}