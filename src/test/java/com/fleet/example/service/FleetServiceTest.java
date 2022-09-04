package com.fleet.example.service;

import com.fleet.example.exception.FleetExistsException;
import com.fleet.example.exception.FleetNotFoundException;
import com.fleet.example.model.Fleet;
import com.fleet.example.repository.FleetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FleetServiceTest {
    private static final long FLEET_ID1 = 1L;
    private static final long FLEET_ID2 = 2L;
    private static final String FLEET1_NAME = "Fleet1";
    private static final String FLEET2_NAME = "Fleet2";
    private static final String FLEET1_VIN = "Fleet1_VIN";
    private static final String FLEET2_VIN = "Fleet2_VIN";
    private static final String FLEET1_PLATE_NUMBER = "Fleet1_Plate_Number";
    private static final String FLEET2_PLATE_NUMBER = "Fleet2_Plate_Number";

    @Autowired
    private FleetService fleetService;
    @MockBean
    private FleetRepository fleetRepository;

    @BeforeEach
    void setUp() {
        Fleet fleet1 = createFleet1();
        Fleet fleet2 = createFleet2();

        when(fleetRepository.findAll()).thenReturn(List.of(fleet1, fleet2));
        when(fleetRepository.findById(FLEET_ID1)).thenReturn(Optional.of(fleet1));
        when(fleetRepository.save(any())).thenReturn(fleet2);
    }

    @Test
    public void findAllFleetsTest() {
        List<Fleet> fleets = fleetService.getAllFleets();
        verify(fleetRepository, times(1)).findAll();
        assertEquals(2, fleets.size());
        assertEquals(FLEET_ID1, fleets.get(0).getId());
        assertEquals(FLEET1_NAME, fleets.get(0).getName());
        assertEquals(FLEET1_VIN, fleets.get(0).getVin());
        assertEquals(FLEET1_PLATE_NUMBER, fleets.get(0).getLicensePlateNumber());
        assertEquals(FLEET_ID2, fleets.get(1).getId());
        assertEquals(FLEET2_NAME, fleets.get(1).getName());
        assertEquals(FLEET2_VIN, fleets.get(1).getVin());
        assertEquals(FLEET2_PLATE_NUMBER, fleets.get(1).getLicensePlateNumber());
    }

    @Test
    public void findFleet_SuccessTest() throws FleetNotFoundException {
        Fleet fleet = fleetService.getFleet(FLEET_ID1);
        verify(fleetRepository, times(1)).findById(FLEET_ID1);
        assertEquals(FLEET_ID1, fleet.getId());
        assertEquals(FLEET1_NAME, fleet.getName());
        assertEquals(FLEET1_VIN, fleet.getVin());
        assertEquals(FLEET1_PLATE_NUMBER, fleet.getLicensePlateNumber());
    }

    @Test
    public void findFleet_NotFoundTest() {
        assertThrows(FleetNotFoundException.class, () -> fleetService.getFleet(FLEET_ID2));
    }

    @Test
    public void createFleet_SuccessTest() throws FleetExistsException {
        Fleet fleet = fleetService.createFleet(createFleet2());
        verify(fleetRepository, times(1)).save(any());
        assertEquals(FLEET_ID2, fleet.getId());
        assertEquals(FLEET2_NAME, fleet.getName());
        assertEquals(FLEET2_VIN, fleet.getVin());
        assertEquals(FLEET2_PLATE_NUMBER, fleet.getLicensePlateNumber());
    }

    @Test
    public void createFleet_FleetExistsTest() {
        assertThrows(FleetExistsException.class, () -> fleetService.createFleet(createFleet1()));
    }

    @Test
    public void deleteFleet() throws FleetNotFoundException {
        fleetService.deleteFleet(FLEET_ID1);
        verify(fleetRepository, times(1)).delete(createFleet1());
    }

    private Fleet createFleet1() {
        Fleet fleet = Fleet.builder()
                .id(FLEET_ID1)
                .name(FLEET1_NAME)
                .vin(FLEET1_VIN)
                .licensePlateNumber(FLEET1_PLATE_NUMBER)
                .properties(null)
                .build();

        return fleet;
    }

    private Fleet createFleet2() {
        Fleet fleet = Fleet.builder()
                .id(FLEET_ID2)
                .name(FLEET2_NAME)
                .vin(FLEET2_VIN)
                .licensePlateNumber(FLEET2_PLATE_NUMBER)
                .properties(null)
                .build();

        return fleet;
    }
}
