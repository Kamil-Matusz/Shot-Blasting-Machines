package com.example.api.seeders;

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
@DependsOn({"roleDataSeeder", "userDataSeeder", "materialDataSeeder", "orderStateDataSeeder"})
public class ModelDataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (modelsNotExists()) {
            List<Model> models = new ArrayList<>();

            Model model1 = new Model();
            model1.setName("Standard");
            model1.setPrice(50000.00);
            model1.setComments("Podstawowy model śrutownicy");
            models.add(model1);

            Model model2 = new Model();
            model2.setName("XL");
            model2.setPrice(10000.00);
            model2.setComments("Model w wersji XL z powiększonym koszem");
            models.add(model2);

            Model model3 = new Model();
            model3.setName("XXL");
            model3.setPrice(10000.00);
            model3.setComments("Model w wersji XXL z maksymalnym koszem");
            models.add(model3);

            for (Model model : models) {
                entityManager.persist(model);
            }
        }
    }

    private boolean modelsNotExists() {
        Long count = entityManager.createQuery("SELECT COUNT(m) FROM Model m", Long.class)
                .getSingleResult();
        return count == 0;
    }
}

