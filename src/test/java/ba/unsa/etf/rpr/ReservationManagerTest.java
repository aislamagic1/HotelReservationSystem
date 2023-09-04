package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.ReservationsManager;
import ba.unsa.etf.rpr.business.RoomTypesManager;
import ba.unsa.etf.rpr.domain.Reservations;
import ba.unsa.etf.rpr.domain.RoomTypes;
import ba.unsa.etf.rpr.exception.ReservationsException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aldin Islamagic
 */

public class ReservationManagerTest {


    @Test
    void getAllTest(){
        assertDoesNotThrow(()->{new ReservationsManager().getAll();});
    }

    @Test
    void getByIdTest(){
        assertThrows(ReservationsException.class, () ->{new ReservationsManager().getById(99);});
    }

    @Test
    void getAllSchedulesForRooms() throws ReservationsException {
        assertEquals(0, new ReservationsManager().getAllSchedulesForRooms(100).size());
    }

}
