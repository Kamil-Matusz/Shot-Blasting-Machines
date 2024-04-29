package com.example.api.seeders;

import com.example.api.model.Model;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ModelDataSeeder {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void seedData() {
        List<Model> models = new ArrayList<>();

        Model model1 = new Model();
        model1.setName("Standard");
        model1.setPrice(50000.00);
        model1.setComments("Podstawowy model śrutownicy");
        models.add(model1);

        Model model2 = new Model();
        model2.setName("XL");
        model2.setPrice(10000.00);
        model2.setComments("Model w wersji XL");
        models.add(model2);

        for (Model model : models) {
            entityManager.persist(model);
        }
    }
}