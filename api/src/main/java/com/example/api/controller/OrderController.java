package com.example.api.controller;

import com.example.api.dto.OrderDTO;
import com.example.api.dto.OrderSaveRequestDTO;
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

import static com.example.api.dto.OrderDTO.convertToDTO;

/**
 * REST controller for managing orders.
 * <p>
 * Handles HTTP requests related to orders.
 * </p>
 */
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

    /**
     * Constructor of the controller, injecting the necessary repositories.
     *
     * @param orderRepository the order repository
     * @param clientRepository the client repository
     * @param userRepository the user repository
     * @param machineRepository the machine repository
     * @param orderStateRepository the order state repository
     * @param modelRepository the model repository
     * @param accessoryRepository the accessory repository
     */
    @Autowired
    public OrderController(OrderRepository orderRepository, ClientRepository clientRepository, UserRepository userRepository, MachineRepository machineRepository, OrderStateRepository orderStateRepository, ModelRepository modelRepository, AccessoryRepository accessoryRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.machineRepository = machineRepository;
        this.orderStateRepository = orderStateRepository;
        this.modelRepository = modelRepository;
        this.accessoryRepository = accessoryRepository;
    }

    /**
     * Adds a new order.
     * <p>
     * Mapped to HTTP POST requests for /api/orders.
     * </p>
     *
     * @param orderSaveRequestDTO the order save request data transfer object
     * @return ResponseEntity containing the created OrderDTO and HTTP status 201 (Created)
     */
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

    /**
     * Retrieves a list of all orders.
     * <p>
     * Mapped to HTTP GET requests for /api/orders.
     * </p>
     *
     * @return ResponseEntity containing a list of OrderDTO and HTTP status 200 (OK)
     */
    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        List<OrderDTO> orderDTOs = orders.stream()
                .map(OrderDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
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
