package com.example.api.repositoriesTests;

import com.example.api.model.Client;
import com.example.api.model.Model;
import com.example.api.repository.ModelRepository;
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
public class ModelControllerTest {

    @Autowired
    private ModelRepository modelRepository;

    @Test
    public void findAllModels() {
        List<Model> models = modelRepository.findAll();
        assertThat(models).isNotNull();
        assertThat(models).hasSizeGreaterThan(0);
    }

    @Test
    public void findModelById() {
        Long modelId = 1L;
        Model model = modelRepository.findById(modelId).orElse(null);
        assertThat(model).isNotNull();
    }

    @Test
    public void findModelByName() {
        String modelName = "Standard";
        Model model = modelRepository.findByName(modelName);
        assertThat(model).isNotNull();
    }
}
