package com.example.api.controller;

import com.example.api.dto.ClientDTO;
import com.example.api.dto.OrderDTO;
import com.example.api.model.Client;
import com.example.api.model.Order;
import com.example.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

//    @PostMapping("")
//    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
//        Order savedOrder = orderRepository.save(order);
//        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
//    }

    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        List<OrderDTO> orderDTOs = orders.stream()
                .map(OrderDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }
}
