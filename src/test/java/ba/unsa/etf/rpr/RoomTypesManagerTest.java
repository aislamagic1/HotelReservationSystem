package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.RoomTypesManager;
import ba.unsa.etf.rpr.business.RoomsManager;
import ba.unsa.etf.rpr.domain.RoomTypes;
import ba.unsa.etf.rpr.exception.RoomTypesException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTypesManagerTest {

    @Test
    void getAllTest(){
        assertDoesNotThrow(() ->{
            List<RoomTypes> roomTypes = new RoomTypesManager().getAll();
        });
    }

    @Test
    void addWithPriceNull(){
        RoomTypes roomType = new RoomTypes();
        roomType.setRoomType("test");
        roomType.setNumPersons(2);
        assertThrows(RoomTypesException.class, ()->{new RoomTypesManager().add(roomType);});
    }
}
