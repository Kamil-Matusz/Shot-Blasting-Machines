package com.example.api.dto;

import com.example.api.model.Order;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private Double price;
    private String date;
    private String comments;
    private String state;
    private ClientDTO client;
    private UserDTO user;
    private MachineDTO machine;

    public static OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setState(order.getState().getName());
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
