package com.example.api.dto;

import com.example.api.model.Machine;
import com.example.api.model.Model;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MachineDTO {
    private Long id;
    private Model model;
    private List<AccesoryDTO> accessories;

    public static MachineDTO convertToDTO(Machine machine) {
        MachineDTO machineDTO = new MachineDTO();
        machineDTO.setId(machine.getId());
        machineDTO.setModel(machine.getModel());

        List<AccesoryDTO> accesoryDTOs = machine.getAccessories().stream()
                .map(AccesoryDTO::convertToDTO)
                .collect(Collectors.toList());

        machineDTO.setAccessories(accesoryDTOs);

        return machineDTO;
    }
}
