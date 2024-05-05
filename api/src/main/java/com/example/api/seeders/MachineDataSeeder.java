package com.example.api.seeders;

import com.example.api.model.Machine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn({"roleDataSeeder", "userDataSeeder", "materialDataSeeder", "orderStateDataSeeder"})
public class MachineDataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        seedData();
    }

    private void seedData() {
        if (modelsNotExists()) {
            List<Machine> machines = new ArrayList<>();

            Machine machine1 = new Machine();
            machine1.setModel_id(1L);
            machines.add(machine1);

            Machine machine2 = new Machine();
            machine2.setModel_id(2L);
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

