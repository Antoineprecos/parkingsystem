package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParkingSpotDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void beforeEach() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    void updateParkingTest() {
        // GIVEN : On veut enregistrer une place disponible de parking
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        // WHEN :
        parkingSpotDAO.updateParking(parkingSpot);

        // THEN :
        int result = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
        assertEquals(2, result);
    }

    @Test
    void getNextAvailableSlotTest() {
        // GIVEN : On veut enregistrer une place disponible de parking
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        parkingSpotDAO.updateParking(parkingSpot);
        ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.CAR, false);
        parkingSpotDAO.updateParking(parkingSpot2);

        // WHEN :
        int result = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);

        // THEN :
        assertEquals(3, result);
    }

    @Test
    void getNextAvailableSlotTest_WithNoSpotAvailable() {
        // GIVEN : On veut enregistrer une place disponible de parking
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        parkingSpotDAO.updateParking(parkingSpot);
        ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.CAR, false);
        parkingSpotDAO.updateParking(parkingSpot2);
        ParkingSpot parkingSpot3 = new ParkingSpot(3, ParkingType.CAR, false);
        parkingSpotDAO.updateParking(parkingSpot3);

        // WHEN :
        int result = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);

        // THEN :
        assertEquals(0, result);
    }

}


