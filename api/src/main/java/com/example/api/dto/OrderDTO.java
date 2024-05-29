package com.example.api.dto;

import com.example.api.model.Order;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for representing order information.
 * <p>
 * OrderDTO is used to transfer order-related data between the frontend and backend of the system.
 * </p>
 */
@Data
public class OrderDTO {
    /** The unique identifier of the order. */
    private Long id;

    /** The price of the order. */
    private Double price;

    /** The date of the order. */
    private String date;

    /** Comments or notes related to the order. */
    private String comments;

    /** The client associated with the order. */
    private ClientDTO client;

    /** The user who placed the order. */
    private UserDTO user;

    /** The machine associated with the order. */
    private MachineDTO machine;

    /**
     * Converts an Order entity to its corresponding DTO representation.
     *
     * @param order The Order entity to convert.
     * @return The OrderDTO representing the given Order entity.
     */
    public static OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setDate(order.getDate().toString());
        orderDTO.setComments(order.getComments());

        ClientDTO clientDTO = ClientDTO.convertToDTO(order.getClient());
        orderDTO.setClient(clientDTO);

        UserDTO userDTO = UserDTO.convertToDTO(order.getUser());
        orderDTO.setUser(userDTO);

        MachineDTO machineDTO = MachineDTO.convertToDTO(order.getMachine());
        orderDTO.setMachine(machineDTO);

        return orderDTO;
    }
}
