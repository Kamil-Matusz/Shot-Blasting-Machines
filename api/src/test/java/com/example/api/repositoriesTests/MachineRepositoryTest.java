package com.example.api.repositoriesTests;

import com.example.api.model.Machine;
import com.example.api.repository.MachineRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MachineRepositoryTest {

    @Autowired
    private MachineRepository machineRepository;

    @Test
    public void findMachineById() {
        Long machineId = 1L;
        Machine machine = machineRepository.findById(machineId).orElse(null);
        assertThat(machine).isNotNull();
    }
}
