package com.example.api.controller;

import com.example.api.dto.*;
import com.example.api.dto.params.OrderParams;
import com.example.api.model.*;
import com.example.api.repository.*;
import com.example.api.utils.PdfGenerator;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final MachineRepository machineRepository;
    private final OrderStateRepository orderStateRepository;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;
    private final AccessoryRepository accessoryRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ClientRepository clientRepository, UserRepository userRepository, MachineRepository machineRepository, OrderStateRepository orderStateRepository, ModelRepository modelRepository, AccessoryRepository accessoryRepository) {
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(orderSaveRequestDTO.getDate(), formatter);
        order.setDate(dateTime);

        order.setPrice(orderSaveRequestDTO.getPrice());
        order.setComments(orderSaveRequestDTO.getComments());

        Model model = modelRepository.findById(orderSaveRequestDTO.getModel()).orElseThrow(() -> new RuntimeException("Model not found"));

        Machine newMachine = new Machine();
        newMachine.setModel(model);

        var accessories = accessoryRepository.findAllById(orderSaveRequestDTO.getAccessories());
        newMachine.setAccessories(accessories);

        machineRepository.save(newMachine);

        order.setMachine(newMachine);

        Client client = clientRepository.findById(orderSaveRequestDTO.getClient()).orElseThrow(() -> new RuntimeException("Client not found"));
        order.setClient(client);

        User user = userRepository.findById(orderSaveRequestDTO.getUser()).orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        OrderState orderState = orderStateRepository.findById(1L).orElseThrow(() -> new RuntimeException("Order state not found"));
        order.setState(orderState);

        orderRepository.save(order);

        order.setSummary(PdfGenerator.createPDF(order));

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

    @GetMapping("/{id}/summary")
    public ResponseEntity<byte[]> getPdfSummary(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getSummary() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "SummaryOrder#" + id + ".pdf");
            headers.setContentLength(order.getSummary().length);
            return new ResponseEntity<>(order.getSummary(), headers, HttpStatus.OK);
        }
    }
}
