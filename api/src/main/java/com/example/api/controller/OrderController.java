package com.example.api.controller;

import com.example.api.dto.OrderDTO;
import com.example.api.dto.OrderSaveRequestDTO;
import com.example.api.model.*;
import com.example.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.api.dto.OrderDTO.convertToDTO;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final MachineRepository machineRepository;
    private final OrderStateRepository orderStateRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ClientRepository clientRepository, UserRepository userRepository, MachineRepository machineRepository, OrderStateRepository orderStateRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.machineRepository = machineRepository;
        this.orderStateRepository = orderStateRepository;
    }

    // TODO: Rozwiazac problem z tym, ze maszyna ma juz istniec przed zapisem zamowienia
    @PostMapping("")
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderSaveRequestDTO orderSaveRequestDTO) {
        Order order = new Order();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(orderSaveRequestDTO.getDate(), formatter);
        order.setDate(dateTime);

        order.setPrice(orderSaveRequestDTO.getPrice());
        order.setComments(orderSaveRequestDTO.getComments());

        Machine machine = machineRepository.findById(orderSaveRequestDTO.getMachine()).orElseThrow(() -> new RuntimeException("Machine not found"));
        order.setMachine(machine);

        Client client = clientRepository.findById(orderSaveRequestDTO.getClient()).orElseThrow(() -> new RuntimeException("Client not found"));
        order.setClient(client);

        User user = userRepository.findById(orderSaveRequestDTO.getUser()).orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        OrderState orderState = orderStateRepository.findById(1L).orElseThrow(() -> new RuntimeException("Order state not found"));
        order.setState(orderState);

        orderRepository.save(order);
        OrderDTO orderDTO = OrderDTO.convertToDTO(order);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        List<OrderDTO> orderDTOs = orders.stream()
                .map(OrderDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }
}
