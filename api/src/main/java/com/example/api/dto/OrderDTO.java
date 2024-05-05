package com.example.api.dto;

import com.example.api.model.Order;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private Double price;
    private LocalDateTime date;
    private String comments;
    private ClientDTO client;
    private UserDTO user;
    private MachineDTO machine;

    public static OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setDate(order.getDate());
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
