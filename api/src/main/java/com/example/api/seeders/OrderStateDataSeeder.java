package com.example.api.seeders;

import com.example.api.model.OrderState;
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
@DependsOn({"materialDataSeeder"})
public class OrderStateDataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (modelsNotExists()) {
            List<OrderState> orderStates = new ArrayList<>();

            OrderState pending = new OrderState();
            pending.setName("Przyjęte");
            orderStates.add(pending);

            OrderState processing = new OrderState();
            processing.setName("Skompletowane");
            orderStates.add(processing);

            OrderState shipped = new OrderState();
            shipped.setName("Wysłane");
            orderStates.add(shipped);

            OrderState delivered = new OrderState();
            delivered.setName("Odebrane");
            orderStates.add(delivered);

            for (OrderState orderState : orderStates) {
                entityManager.persist(orderState);
            }
        }
    }

    private boolean modelsNotExists() {
        Long count = entityManager.createQuery("SELECT COUNT(o) FROM OrderState o", Long.class)
                .getSingleResult();
        return count == 0;
    }
}

