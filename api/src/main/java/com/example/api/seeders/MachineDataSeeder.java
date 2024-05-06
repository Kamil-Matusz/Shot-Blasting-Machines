package com.example.api.seeders;

import com.example.api.model.Machine;
import com.example.api.model.Model;
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
@DependsOn({"roleDataSeeder", "userDataSeeder", "materialDataSeeder", "orderStateDataSeeder", "modelDataSeeder"})
public class MachineDataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        seedData();
    }

    private void seedData() {
        Session session = entityManager.unwrap(Session.class);
        List<Model> models = session.createQuery("FROM Model ", Model.class).getResultList();

        if (modelsNotExists()) {
            List<Machine> machines = new ArrayList<>();

            Machine machine1 = new Machine();
            machine1.setModel(models.stream().filter(model -> model.getName().equals("Standard")).findFirst().orElse(null));
            machines.add(machine1);

            Machine machine2 = new Machine();
            machine2.setModel(models.stream().filter(model -> model.getName().equals("XL")).findFirst().orElse(null));
            machines.add(machine2);

            for (Machine machine : machines) {
                entityManager.persist(machine);
            }
        }
    }

    private boolean modelsNotExists() {
        Long count = entityManager.createQuery("SELECT COUNT(m) FROM Machine m", Long.class)
                .getSingleResult();
        return count == 0;
    }
}

