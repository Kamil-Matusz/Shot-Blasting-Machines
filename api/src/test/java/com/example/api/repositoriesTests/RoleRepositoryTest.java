package com.example.api.repositoriesTests;

import com.example.api.model.Client;
import com.example.api.model.Role;
import com.example.api.repository.ClientRepository;
import com.example.api.repository.RoleRepository;
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
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        assertThat(roles).isNotNull();
        assertThat(roles).hasSizeGreaterThan(0);
    }
}
