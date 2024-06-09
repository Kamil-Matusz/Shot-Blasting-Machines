package com.example.api.controller;

import com.example.api.dto.*;
import com.example.api.model.*;
import com.example.api.repository.*;
import com.example.api.utils.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
    private final MaterialRepository materialRepository;

    /**
     * Constructor of the controller, injecting the necessary repositories.
     *
     * @param orderRepository      the order repository
     * @param clientRepository     the client repository
     * @param userRepository       the user repository
     * @param machineRepository    the machine repository
     * @param orderStateRepository the order state repository
     * @param modelRepository      the model repository
     * @param accessoryRepository  the accessory repository
     */
    @Autowired
    public OrderController(OrderRepository orderRepository, ClientRepository clientRepository,
            UserRepository userRepository, MachineRepository machineRepository,
            OrderStateRepository orderStateRepository, ModelRepository modelRepository,
            AccessoryRepository accessoryRepository, MaterialRepository materialRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.machineRepository = machineRepository;
        this.orderStateRepository = orderStateRepository;
        this.modelRepository = modelRepository;
        this.accessoryRepository = accessoryRepository;
        this.materialRepository = materialRepository;
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
    public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestParam(required = false) Long stateId) {
        List<Order> orders = orderRepository.findAll();

        List<OrderDTO> orderDTOs = orders.stream()
                .filter(order -> {
                    boolean matches = true;
                    if (stateId != null) {
                        if (OrderStateEnum.IN_PRODUCTION.getValue() == stateId) {
                            matches = order.getState().getId().equals(OrderStateEnum.IN_PRODUCTION.getValue())
                                    || order.getState().getId().equals(OrderStateEnum.TO_BE_CORRECTED.getValue());
                        } else {
                            matches = order.getState().getId().equals(stateId);
                        }
                    }
                    return matches;
                })
                .map(OrderDTO::convertToDTO)

                .collect(Collectors.toList());

        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (!order.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderDTO orderdto = order.map(OrderDTO::convertToDTO).get();

        return new ResponseEntity<>(orderdto, HttpStatus.OK);
    }

    /**
     * Adds a new order.
     * <p>
     * Mapped to HTTP POST requests for /api/orders.
     * </p>
     *
     * @param orderSaveRequestDTO the order save request data transfer object
     * @return ResponseEntity containing the created OrderDTO and HTTP status 201
     *         (Created)
     */
    @PostMapping("")
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderSaveRequestDTO orderSaveRequestDTO) {
        Order order = new Order();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(orderSaveRequestDTO.getDate(), formatter);
        order.setDate(dateTime);

        order.setPrice(orderSaveRequestDTO.getPrice());
        order.setComments(orderSaveRequestDTO.getComments());

        Model model = modelRepository.findById(orderSaveRequestDTO.getModel())
                .orElseThrow(() -> new RuntimeException("Model not found"));

        Machine newMachine = new Machine();
        newMachine.setModel(model);

        var accessories = accessoryRepository.findAllById(orderSaveRequestDTO.getAccessories());
        newMachine.setAccessories(accessories);

        machineRepository.save(newMachine);

        order.setMachine(newMachine);

        Client client = clientRepository.findById(orderSaveRequestDTO.getClient())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        order.setClient(client);

        User user = userRepository.findById(orderSaveRequestDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        OrderState orderState = orderStateRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Order state not found"));
        order.setState(orderState);

        orderRepository.save(order);

        order.setSummary(PdfGenerator.createPDF(order));

        orderRepository.save(order);
        OrderDTO orderDTO = OrderDTO.convertToDTO(order);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
    /**
     * Starts the production process for a specific order.
     * <p>
     * Mapped to HTTP POST requests for /api/orders/{id}/start-production.
     * </p>
     *
     * @param id the ID of the order to start production for
     * @return ResponseEntity with HTTP status 200 (OK) if successful, 404 (Not Found) if the order does not exist,
     * or 400 (Bad Request) if the order is not in a valid state to start production or if there are not enough materials
     */
    @PostMapping("/{id}/start-production")
    public ResponseEntity startProduction(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();
        if (order.getState().getId() != (long) OrderStateEnum.NEW.getValue())
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository.findById((long) OrderStateEnum.IN_PRODUCTION.getValue());

        var neededMaterials = order.getMachine().getModel().getNeededMaterials();

        if (!decreaseMaterials(neededMaterials)) {
            return new ResponseEntity<>("Niewystarczająca ilośc materiałów", HttpStatus.BAD_REQUEST);
        }

        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Marks an order as ready to be checked.
     * <p>
     * Mapped to HTTP POST requests for /api/orders/{id}/to-check.
     * </p>
     *
     * @param id the ID of the order to mark as ready to be checked
     * @return ResponseEntity with HTTP status 200 (OK) if successful, 404 (Not Found) if the order does not exist,
     * or 400 (Bad Request) if the order is not in a valid state to be marked as ready to be checked
     */
    @PostMapping("/{id}/to-check")
    public ResponseEntity markAsReadyToCheck(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();
        if (order.getState().getId() != (long) OrderStateEnum.IN_PRODUCTION.getValue() &&
                order.getState().getId() != (long) OrderStateEnum.TO_BE_CORRECTED.getValue())
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository.findById((long) OrderStateEnum.TO_BE_CHECKED.getValue());
        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Marks an order as sent to the client.
     * <p>
     * Mapped to HTTP POST requests for /api/orders/{id}/send-to-client.
     * </p>
     *
     * @param id the ID of the order to mark as sent to the client
     * @return ResponseEntity with HTTP status 200 (OK) if successful, 404 (Not Found) if the order does not exist,
     * or 400 (Bad Request) if the order is not in a valid state to be marked as sent to the client
     */
    @PostMapping("/{id}/send-to-client")
    public ResponseEntity sendToClient(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();

        if (order.getState().getId() != (long) OrderStateEnum.NEW.getValue())
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository.findById((long) OrderStateEnum.COMPLETED.getValue());
        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Confirms the quality of an order.
     * <p>
     * Mapped to HTTP POST requests for /api/orders/{id}/quality-confirm.
     * </p>
     *
     * @param id the ID of the order to confirm quality for
     * @return ResponseEntity with HTTP status 200 (OK) if successful, 404 (Not Found) if the order does not exist,
     * or 400 (Bad Request) if the order is not in a valid state to be confirmed for quality
     */
    @PostMapping("/{id}/quality-confirm")
    public ResponseEntity qualityConfirm(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();

        if (order.getState().getId() != (long) OrderStateEnum.IN_PRODUCTION.getValue())
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository.findById((long) OrderStateEnum.READY.getValue());
        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Declines the quality of an order.
     * <p>
     * Mapped to HTTP POST requests for /api/orders/{id}/quality-decline.
     * </p>
     *
     * @param id the ID of the order to decline quality for
     * @return ResponseEntity with HTTP status 200 (OK) if successful, 404 (Not Found) if the order does not exist,
     * or 400 (Bad Request) if the order is not in a valid state to be declined for quality
     */
    @PostMapping("/{id}/quality-decline")
    public ResponseEntity qualityDecline(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order order = optionalOrder.get();

        if (order.getState().getId() != (long) OrderStateEnum.IN_PRODUCTION.getValue())
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<OrderState> orderState = orderStateRepository
                .findById((long) OrderStateEnum.TO_BE_CORRECTED.getValue());
        order.setState(orderState.get());

        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Retrieves the PDF summary of a specific order.
     * <p>
     * Mapped to HTTP GET requests for /api/orders/{id}/summary.
     * </p>
     *
     * @param id the ID of the order to retrieve the summary for
     * @return ResponseEntity containing the PDF summary as a byte array and HTTP status 200 (OK),
     * or HTTP status 404 (Not Found) if the order or summary does not exist
     */
    @GetMapping("/{id}/summary")
    public ResponseEntity<byte[]> getBlobSummary(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getSummary() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(order.getSummary(), HttpStatus.OK);
        }
    }

    /**
     * Decreases the amount of materials needed for production.
     *
     * @param neededMaterials the list of needed materials
     * @return true if materials were successfully decreased, false if there are not enough materials
     */
    public boolean decreaseMaterials(List<NeededMaterials> neededMaterials) {
        for (NeededMaterials neededMaterial : neededMaterials) {
            Material material = neededMaterial.getMaterial();
            int requiredAmount = neededMaterial.getAmount();

            if (material.getAmount() < requiredAmount) {
                return false;
            }

            material.setAmount(material.getAmount() - requiredAmount);
            materialRepository.save(material);
        }
        return true;
    }

}
