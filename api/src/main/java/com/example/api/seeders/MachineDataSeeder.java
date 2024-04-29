package com.example.api.seeders;

import com.example.api.model.Accesory;
import com.example.api.model.Machine;
import com.example.api.model.Model;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MachineDataSeeder {

    @PersistenceContext
    private EntityManager entityManager;
    @PostConstruct
    @Transactional
    public void seedData() {
        Accesory accessory1 = new Accesory();
        accessory1.setName("Zawieszka");
        entityManager.persist(accessory1);


        Session session = entityManager.unwrap(Session.class);
        List<Model> models = session.createQuery("FROM Model", Model.class).getResultList();

        List<Machine> machines = new ArrayList<>();

        for (Model model : models) {
            Machine machine = new Machine();
            machine.setModel_id(model.getId());
            List<Accesory> accessories = new ArrayList<>();
            accessories.add(accessory1);
            machine.setAccessories(accessories);
            machines.add(machine);
        }

        for (Machine machine : machines) {
            entityManager.persist(machine);
        }
    }
}
