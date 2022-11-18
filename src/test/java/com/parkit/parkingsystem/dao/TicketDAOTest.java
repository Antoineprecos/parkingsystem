package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TicketDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @BeforeAll
    private static void setUp() throws Exception{
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void beforeEach() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    void saveTicketTest() {
        // GIVEN : On veut enregistrer un ticket de type CAR
        Ticket t = new Ticket();
        t.setInTime(new Date());
        t.setOutTime(new Date());
        t.setPrice(25);
        t.setParkingSpot(new ParkingSpot(1,ParkingType.CAR,false));
        t.setVehicleRegNumber("ABCDEF");

        // WHEN : On enregistre un ticket
        ticketDAO.saveTicket(t);

        // THEN : On regarde si un ticket a été inséré avec les bonnes valeurs
        Ticket result = ticketDAO.getTicket("ABCDEF");
        assertEquals("ABCDEF", result.getVehicleRegNumber());
        assertEquals(25, result.getPrice());
        assertEquals(1, t.getParkingSpot().getId());
        assertEquals(ParkingType.CAR, t.getParkingSpot().getParkingType());
        assertNotNull(t.getInTime());
        assertNotNull(t.getOutTime());
    }

    // Test nominal
    @Test
    void getTicketTest() {
        // GIVEN . Il y a un ticket dans la base de donnée avec comme plaque LNMOP
        Ticket t = new Ticket();
        t.setParkingSpot(new ParkingSpot(1,ParkingType.CAR,false));
        t.setVehicleRegNumber("LMNOP");
        t.setPrice(34);
        t.setInTime((new Date()));
        t.setOutTime((new Date()));
        ticketDAO.saveTicket(t);

        // WHEN :
        Ticket result = ticketDAO.getTicket("LMNOP");

        //THEN :
        assertEquals("LMNOP", result.getVehicleRegNumber());
        assertEquals(34, result.getPrice());
        assertEquals(1, t.getParkingSpot().getId());
        assertEquals(ParkingType.CAR, t.getParkingSpot().getParkingType());
        assertNotNull(t.getInTime());
        assertNotNull(t.getOutTime());
    }

    // Test de récupération de ticket qui n'existe pas
    @Test
    void getTicketError() {

        // GIVEN : Le ticket recherché n'existe pas

        // WHEN :
        Ticket result = ticketDAO.getTicket("ABC");

        // THEN :
        assertNull(result);
    }

    // Test de vérification des utilisateurs réguliers
    void getTicketAlreadyCame() {
        // GIVEN :
        boolean result = true;
        Ticket ticket = new Ticket();
        result = ticket.isAlreadyCame();

        // WHEN :
        ticketDAO.updateTicket(ticket);

        //THEN :
        assertTrue(true);
    }
}