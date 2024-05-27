package com.example.api.controller;

import com.example.api.dto.*;
import com.example.api.dto.params.OrderParams;
import com.example.api.model.*;
import com.example.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.jaxb.SpringDataJaxb;
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
    private final ModelRepository modelRepository;
    private final AccesoryRepository accessoryRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ClientRepository clientRepository, UserRepository userRepository, MachineRepository machineRepository, OrderStateRepository orderStateRepository, ModelRepository modelRepository, AccesoryRepository accessoryRepository,MaterialRepository materialRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.machineRepository = machineRepository;
        this.orderStateRepository = orderStateRepository;
        this.modelRepository = modelRepository;
        this.accessoryRepository = accessoryRepository;
        this.materialRepository = materialRepository;
    }
    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestParam(required = false) Long stateId) {
        List<Order> orders = orderRepository.findAll();

        List<OrderDTO> orderDTOs = orders.stream()
                .filter(order -> {
                    boolean matches = true;
                    if (stateId!= null) {
                        matches = matches && order.getState().getId().equals(stateId);
                    }
                    // Add more filtering logic as needed
                    return matches;
                })
                .map(OrderDTO::convertToDTO)

                .collect(Collectors.toList());

        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if(!order.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderDTO orderdto = order.map(OrderDTO::convertToDTO).get();

        return new ResponseEntity<>(orderdto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderSaveRequestDTO orderSaveRequestDTO) {
        Order order = new Order();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(orderSaveRequestDTO.getDate(), formatter);
        order.setDate(dateTime);

        order.setPrice(orderSaveRequestDTO.getPrice());
        order.setComments(orderSaveRequestDTO.getComments());

        Model model = modelRepository.findById(orderSaveRequestDTO.getModel()).orElseThrow(() -> new RuntimeException("Model not found"));

        Machine newMachine = new Machine();
        newMachine.setModel(model);

        var accesories = accessoryRepository.findAllById(orderSaveRequestDTO.getAccesories());
        newMachine.setAccessories(accesories);

        machineRepository.save(newMachine);

        order.setMachine(newMachine);

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

    @PostMapping("/{id}/start-production")
    public ResponseEntity startProduction(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();
        if(order.getState().getId() != (long)OrderStateEnum.NEW.getValue())new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository.findById((long)OrderStateEnum.IN_PRODUCTION.getValue());

        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/{id}/to-check")
    public ResponseEntity markAsReadyToCheck(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();
        if(order.getState().getId() != (long)OrderStateEnum.IN_PRODUCTION.getValue() &&
            order.getState().getId() != (long)OrderStateEnum.TO_BE_CORRECTED.getValue()
        )new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository.findById((long)OrderStateEnum.TO_BE_CHECKED.getValue());
        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/{id}/send-to-client")
    public ResponseEntity sendToClient(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();

        if(order.getState().getId() != (long)OrderStateEnum.NEW.getValue())new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository.findById((long)OrderStateEnum.COMPLETED.getValue());
        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/quality-confirm")
    public ResponseEntity qualityConfirm(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();

        if(order.getState().getId() != (long)OrderStateEnum.IN_PRODUCTION.getValue())new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository.findById((long)OrderStateEnum.READY.getValue());
        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/{id}/quality-decline")
    public ResponseEntity qualityDecline(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();

        if(order.getState().getId() != (long)OrderStateEnum.IN_PRODUCTION.getValue())new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository.findById((long)OrderStateEnum.TO_BE_CORRECTED.getValue());
        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
