package com.example.api.seeders;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class SeedersScript {

    @Autowired
    private UserDataSeeder userDataSeeder;

    @Autowired
    private RoleDataSeeder roleDataSeeder;

    @Autowired
    private ClientDataSeeder clientDataSeeder;

    @Autowired
    private MaterialDataSeeder materialDataSeeder;

    @Autowired
    private ModelDataSeeder modelDataSeeder;

    @Autowired
    private MachineDataSeeder machineDataSeeder;

    @PostConstruct
    @Transactional
    public void seedData() {
        roleDataSeeder.seedData();
        userDataSeeder.seedData();
        materialDataSeeder.seedData();
        modelDataSeeder.seedData();
        clientDataSeeder.seedData();
        machineDataSeeder.seedData();
    }
}
