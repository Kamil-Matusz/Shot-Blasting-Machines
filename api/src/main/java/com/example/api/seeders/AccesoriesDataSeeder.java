package com.example.api.seeders;

import com.example.api.model.Accesory;
import com.example.api.model.Model;
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
@DependsOn({"roleDataSeeder", "userDataSeeder", "materialDataSeeder", "orderStateDataSeeder", "modelDataSeeder"})
public class AccesoriesDataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        List<Accesory> accesories = new ArrayList<>();

        Accesory accessory1 = new Accesory();
        accessory1.setName("Zawieszka");
        accessory1.setPrice(50.00);
        entityManager.persist(accessory1);

        for (Accesory accesory : accesories) {
            entityManager.persist(accesory);
        }
    }
}
