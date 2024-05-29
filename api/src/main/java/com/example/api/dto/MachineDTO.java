package com.example.api.dto;

import com.example.api.model.Machine;
import com.example.api.model.Model;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) for representing a machine.
 * <p>
 * MachineDTO is used to transfer machine data, including its ID, associated model,
 * and accessories, between different layers of the application.
 * </p>
 */
@Data
public class MachineDTO {

    /** The ID of the machine. */
    private Long id;

    /** The model associated with the machine. */
    private Model model;

    /** The list of accessories associated with the machine. */
    private List<AccessoryDTO> accessories;

    /** The list of accessories associated with the machine. */

    /**
     * Converts a Machine entity to a MachineDTO.
     *
     * @param machine The Machine entity to be converted.
     * @return The corresponding MachineDTO object.
     */
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
