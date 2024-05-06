package com.example.api.seeders;

import com.example.api.model.Material;
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
@DependsOn({"clientDataSeeder"})
public class MaterialDataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (modelsNotExists()) {
            List<Material> materials = new ArrayList<>();

            Material perforatedSheet = new Material();
            perforatedSheet.setName("Blacha Perforowana");
            perforatedSheet.setPrice(10.50);
            perforatedSheet.setAmount(100);
            materials.add(perforatedSheet);

            Material rotorBlade = new Material();
            rotorBlade.setName("Łopata wirnika");
            rotorBlade.setPrice(30.00);
            rotorBlade.setAmount(50);
            materials.add(rotorBlade);

            Material transformer = new Material();
            transformer.setName("Transformator");
            transformer.setPrice(2000.00);
            transformer.setAmount(10);
            materials.add(transformer);

            Material picturesquePowder = new Material();
            picturesquePowder.setName("Proszek malowniczy");
            picturesquePowder.setPrice(50.00);
            picturesquePowder.setAmount(10);
            materials.add(picturesquePowder);

            Material teflon1 = new Material();
            teflon1.setName("Śruby z teflonem");
            teflon1.setPrice(0.20);
            teflon1.setAmount(5000);
            materials.add(teflon1);

            Material teflon2 = new Material();
            teflon2.setName("Śruby bez teflonu");
            teflon2.setPrice(0.10);
            teflon2.setAmount(5000);
            materials.add(teflon2);

            Material fan = new Material();
            fan.setName("Wentylator");
            fan.setPrice(300.00);
            fan.setAmount(100);
            materials.add(fan);

            Material powerSocket = new Material();
            powerSocket.setName("Gniazdo Zasilające");
            powerSocket.setPrice(20.00);
            powerSocket.setAmount(400);
            materials.add(powerSocket);

            Material stickers = new Material();
            stickers.setName("Naklejka");
            stickers.setPrice(0.05);
            stickers.setAmount(4000);
            materials.add(stickers);

            Material hmiPanel = new Material();
            hmiPanel.setName("Panel HMI");
            hmiPanel.setPrice(400.00);
            hmiPanel.setAmount(100);
            materials.add(hmiPanel);

            Material contactor = new Material();
            contactor.setName("Stycznik");
            contactor.setPrice(29.00);
            contactor.setAmount(700);
            materials.add(contactor);

            for (Material material : materials) {
                entityManager.persist(material);
            }
        }
    }

    private boolean modelsNotExists() {
        Long count = entityManager.createQuery("SELECT COUNT(m) FROM Material m", Long.class)
                .getSingleResult();
        return count == 0;
    }
}
