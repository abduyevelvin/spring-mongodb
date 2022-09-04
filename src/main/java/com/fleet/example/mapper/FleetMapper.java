package com.fleet.example.mapper;

import com.fleet.example.dto.FleetDTO;
import com.fleet.example.model.Fleet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FleetMapper {
    FleetDTO fleetToFleetDTO(Fleet fleet);
    List<FleetDTO> fleetsToFleetDTOs(List<Fleet> fleets);
}
