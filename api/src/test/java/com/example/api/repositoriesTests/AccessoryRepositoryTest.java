package com.example.api.repositoriesTests;

import com.example.api.model.Accessory;
import com.example.api.model.Client;
import com.example.api.repository.AccessoryRepository;
import com.example.api.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccessoryRepositoryTest {

    @Autowired
    private AccessoryRepository accesoryRepository;

    @Test
    public void findAllClients() {
        List<Accessory> accesories = accesoryRepository.findAll();
        assertThat(accesories).isNotNull();
        assertThat(accesories).hasSizeGreaterThan(0);
    }

    @Test
    public void findClientById() {
        Long accessoryId = 1L;
        Accessory accesory = accesoryRepository.findById(accessoryId).orElse(null);
        assertThat(accessoryId).isNotNull();
    }
}
