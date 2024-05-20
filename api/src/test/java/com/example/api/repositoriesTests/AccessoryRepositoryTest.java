package com.example.api.repositoriesTests;

import com.example.api.model.Accesory;
import com.example.api.model.Client;
import com.example.api.repository.AccesoryRepository;
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
    private AccesoryRepository accesoryRepository;

    @Test
    public void findAllClients() {
        List<Accesory> accesories = accesoryRepository.findAll();
        assertThat(accesories).isNotNull();
        assertThat(accesories).hasSizeGreaterThan(0);
    }

    @Test
    public void findClientById() {
        Long accessoryId = 1L;
        Accesory accesory = accesoryRepository.findById(accessoryId).orElse(null);
        assertThat(accessoryId).isNotNull();
    }
}
