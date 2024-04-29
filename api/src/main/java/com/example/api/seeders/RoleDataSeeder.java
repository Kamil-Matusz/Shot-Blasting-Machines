package com.example.api.seeders;

import com.example.api.model.Role;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;


@Configuration
public class RoleDataSeeder {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void seedData() {
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
