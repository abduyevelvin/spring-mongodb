package com.fleet.example.controller;

import com.fleet.example.dto.FleetDTO;
import com.fleet.example.exception.FleetExistsException;
import com.fleet.example.exception.FleetNotFoundException;
import com.fleet.example.mapper.FleetMapper;
import com.fleet.example.model.Fleet;
import com.fleet.example.service.FleetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Validated
@RequiredArgsConstructor
@Api("Fleet Controller")
public class FleetController {
    private final FleetService fleetService;
    private final FleetMapper mapper = Mappers.getMapper(FleetMapper.class);

    @GetMapping(value = "fleets", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("returns all fleets from DB")
    public ResponseEntity<List<FleetDTO>> getAllFleets() {
        List<Fleet> fleets = fleetService.getAllFleets();

        return new ResponseEntity<>(mapper.fleetsToFleetDTOs(fleets), HttpStatus.OK);
    }

    @GetMapping(value = "/fleets/{fleetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "returns fleet by id or not found exception",
            notes = "need to provide existing fleet id")
    public ResponseEntity<FleetDTO> getFleet(@PathVariable("fleetId") Long fleetId) throws FleetNotFoundException {
        Fleet fleet = fleetService.getFleet(fleetId);

        return new ResponseEntity<>(mapper.fleetToFleetDTO(fleet), HttpStatus.OK);
    }

    @PostMapping(value="/fleets", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "returns created fleet", notes = "need to provide valid fleet for creation")
    public ResponseEntity<FleetDTO> createFleet(@Valid @RequestBody Fleet fleet) throws FleetExistsException {
        Fleet createdFleet = fleetService.createFleet(fleet);

        return new ResponseEntity<>(mapper.fleetToFleetDTO(createdFleet), HttpStatus.CREATED);
    }

    @PutMapping(value="/fleets/{fleetId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "returns updated fleet or not found exception",
            notes = "need to provide valid fleet and existing fleet id to update a fleet")
    public ResponseEntity<FleetDTO> updateFleet(@PathVariable("fleetId") Long fleetId,
                                             @Valid @RequestBody Fleet fleet) throws FleetNotFoundException {
        Fleet updatedFleet = fleetService.updateFleet(fleetId, fleet);

        return new ResponseEntity<>(mapper.fleetToFleetDTO(updatedFleet), HttpStatus.OK);
    }

    @DeleteMapping(value = "/fleets/{fleetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "returns a message the fleet is deleted or not found exception",
            notes = "need to provide existing fleet id")
    public ResponseEntity<String> deleteFleet(@PathVariable("fleetId") Long fleetId) throws FleetNotFoundException {
        fleetService.deleteFleet(fleetId);

        return new ResponseEntity<>(String.format("Fleet is deleted by id: %s", fleetId), HttpStatus.NO_CONTENT);
    }
}
