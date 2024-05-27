package com.example.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NeededMaterialsAddDTO {
    private int id;
    private int amount;

    public NeededMaterialsAddDTO(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }
}
