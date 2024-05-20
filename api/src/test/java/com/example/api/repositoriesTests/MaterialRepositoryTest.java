package com.example.api.repositoriesTests;

import com.example.api.model.Material;
import com.example.api.repository.MaterialRepository;
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
public class MaterialRepositoryTest {

    @Autowired
    private MaterialRepository materialRepository;

    @Test
    public void findAllMaterials() {
        List<Material> materials = materialRepository.findAll();
        assertThat(materials).isNotNull();
        assertThat(materials).hasSizeGreaterThan(0);
    }
}
