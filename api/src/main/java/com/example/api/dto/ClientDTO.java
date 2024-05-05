package com.example.api.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
