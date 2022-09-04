package com.fleet.example.service;

import com.fleet.example.exception.FleetExistsException;
import com.fleet.example.exception.FleetNotFoundException;
import com.fleet.example.model.Fleet;

import java.util.List;

public interface FleetService {
    List<Fleet> getAllFleets();
    Fleet getFleet(Long fleetId) throws FleetNotFoundException;
    Fleet createFleet(Fleet fleet) throws FleetExistsException;
    Fleet updateFleet(Long fleetId, Fleet fleetDetails) throws FleetNotFoundException;
    void deleteFleet(Long fleetId) throws FleetNotFoundException;
}
