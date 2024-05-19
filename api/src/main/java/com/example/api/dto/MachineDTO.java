package com.example.api.dto;

import com.example.api.model.Machine;
import com.example.api.model.Model;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MachineDTO {
    private Long id;
    private Model model;
    private List<AccessoryDTO> accessories;

    public static MachineDTO convertToDTO(Machine machine) {
        MachineDTO machineDTO = new MachineDTO();
        machineDTO.setId(machine.getId());
        machineDTO.setModel(machine.getModel());

        if (machine.getAccessories() != null) {
            List<AccessoryDTO> accessoryDTOS = machine.getAccessories().stream()
                    .map(AccessoryDTO::convertToDTO)
                    .collect(Collectors.toList());

            machineDTO.setAccessories(accessoryDTOS);
        }
        else machineDTO.setAccessories(new ArrayList<AccessoryDTO>());

        return machineDTO;
    }
}
