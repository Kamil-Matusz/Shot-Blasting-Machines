package com.example.api.seeders;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Component responsible for seeding the database with initial data.
 */
@Component
public class DatabaseSeeder implements ApplicationRunner {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Runs the database seeding process at application startup.
     *
     * @param args command-line arguments passed to the application
     * @throws Exception if an error occurs during the seeding process
     */
    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            if (!modelsNotExists()) {
                return;
            }

            Resource resource = resourceLoader.getResource("classpath:data.sql");
            ScriptUtils.executeSqlScript(connection, resource);
        }
    }

    /**
     * Checks if the models table is empty.
     *
     * @return true if the models table is empty, false otherwise
     */
    private boolean modelsNotExists() {
        Long count = entityManager.createQuery("SELECT COUNT(m) FROM Model m", Long.class)
                .getSingleResult();
        return count == 0;
    }
}
