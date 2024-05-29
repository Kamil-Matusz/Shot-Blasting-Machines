package com.example.api;

import com.example.api.model.*;
import com.example.api.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SeedersIntegrationTests {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private AccessoryRepository accesoryRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private MachineRepository machineRepository;

    @Test
    public void clientSedersTests() {
        List<Client> entities = clientRepository.findAll();
        assertThat(entities).hasSizeGreaterThan(0);
    }

    @Test
    public void roleSedersTests() {
        List<Role> entities = roleRepository.findAll();
        assertThat(entities).hasSize(5);
    }

    @Test
    public void userSedersTests() {
        List<User> entities = userRepository.findAll();
        assertThat(entities).hasSizeGreaterThan(0);
    }

    @Test
    public void materialSedersTests() {
        List<Material> entities = materialRepository.findAll();
        assertThat(entities).hasSizeGreaterThan(0);
    }

    @Test
    public void accessorySedersTests() {
        List<Accessory> entities = accesoryRepository.findAll();
        assertThat(entities).hasSize(1);
    }

    @Test
    public void modelSedersTests() {
        List<Model> entities = modelRepository.findAll();
        assertThat(entities).hasSize(3);
    }

    @Test
    public void machineSedersTests() {
        List<Machine> entities = machineRepository.findAll();
        assertThat(entities).hasSize(3);
    }

    @Test
    public void testUserRoleRelationship() {
        Optional<User> userOptional = userRepository.findById(1L);
        assertThat(userOptional).isPresent();

        User user = userOptional.get();
        assertThat(user.getRole()).isNotNull();

        Role role = user.getRole();

        Optional<Role> roleOptional = roleRepository.findById(role.getId());
        assertThat(roleOptional).isPresent();
    }
}

