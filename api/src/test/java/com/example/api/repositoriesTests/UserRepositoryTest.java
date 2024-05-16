package com.example.api.repositoriesTests;

import com.example.api.model.Model;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAllUsers() {
        List<User> users = userRepository.findAll();
        assertThat(users).isNotNull();
        assertThat(users).hasSizeGreaterThan(0);
    }

    @Test
    public void findUserById() {
        Long userId = 1L;
        User user = userRepository.findById(userId).orElse(null);
        assertThat(user).isNotNull();
    }

    @Test
    public void findUserByName() {
        String userName = "Admin";
        User user = userRepository.findByName(userName);
        assertThat(user).isNotNull();
    }

    @Test
    public void findUserByEmail() {
        String email = "admin@blastmachine.com";
        Optional<User> user = userRepository.findByEmail(email);
        assertThat(email).isNotNull();
    }
}
