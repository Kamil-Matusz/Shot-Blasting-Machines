package com.example.api.repositoriesTests;

import com.example.api.model.Order;
import com.example.api.repository.OrderRepository;
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
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).isNotNull();
        assertThat(orders).hasSizeGreaterThan(0);
    }
}
