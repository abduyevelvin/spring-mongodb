package com.fleet.example.service.impl;

import com.fleet.example.exception.FleetExistsException;
import com.fleet.example.exception.FleetNotFoundException;
import com.fleet.example.model.Fleet;
import com.fleet.example.repository.FleetRepository;
import com.fleet.example.service.FleetService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FleetServiceImpl implements FleetService {
    private static final Logger LOG = LoggerFactory.getLogger(FleetServiceImpl.class);
    private final FleetRepository fleetRepository;

    @Override
    public List<Fleet> getAllFleets() {
        LOG.info("Fetching all fleets from DB...");
        return fleetRepository.findAll();
    }

    @Override
    public Fleet getFleet(Long fleetId) throws FleetNotFoundException {

        Fleet fleet = fleetRepository.findById(fleetId)
                .orElseThrow(() -> {
                    LOG.error(String.format("Fleet not found with id: %s", fleetId));
                    return new FleetNotFoundException(String.format("Fleet not found with id: %s", fleetId));
                });

        LOG.info(String.format("Fetching fleet by id: %s", fleetId));
        return fleet;
    }

    @Override
    public Fleet createFleet(Fleet fleet) throws FleetExistsException {

        Optional<Fleet> dbFleet = fleetRepository.findById(fleet.getId());

        if (dbFleet.isPresent()) {
            LOG.error(String.format("Fleet already exists with id: %s", fleet.getId()));
            throw new FleetExistsException(String.format("Fleet already exists with id: %s", fleet.getId()));
        }

        LOG.info("Saving fleet to the DB...");
        return fleetRepository.save(fleet);
    }

    @Override
    public Fleet updateFleet(Long fleetId, Fleet fleetDetails) throws FleetNotFoundException {
        Fleet fleet = fleetRepository.findById(fleetId)
                .orElseThrow(() ->{
                    LOG.error(String.format("Fleet not found with id: %s", fleetId));
                    return new FleetNotFoundException(String.format("Fleet not found with id: %s", fleetId));
                });

        fleet.setName(fleetDetails.getName());
        fleet.setVin(fleetDetails.getVin());
        fleet.setLicensePlateNumber(fleetDetails.getLicensePlateNumber());
        fleet.setProperties(fleetDetails.getProperties());

        LOG.info(String.format("Updating fleet with id: %s", fleetId));
        return fleetRepository.save(fleet);
    }

    @Override
    public void deleteFleet(Long fleetId) throws FleetNotFoundException {
        Fleet fleet = fleetRepository.findById(fleetId)
                .orElseThrow(() ->{
                    LOG.error(String.format("Fleet not found with id: %s", fleetId));
                    return new FleetNotFoundException(String.format("Fleet not found with id: %s", fleetId));
                });

        LOG.info(String.format("Deleting fleet with id: %s", fleetId));
        fleetRepository.delete(fleet);;
    }
}
