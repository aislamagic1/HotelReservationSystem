package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.RoomsManager;
import ba.unsa.etf.rpr.domain.Rooms;
import ba.unsa.etf.rpr.exception.RoomsException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoomsManagerTest {

    @Test
    void getAllTest(){
        assertDoesNotThrow(() ->{
            List<Rooms> list = new RoomsManager().getAll();
        });
    }

    @Test
    void addWithWrongIdTest(){
        RoomsManager manager = new RoomsManager();
        Rooms room = new Rooms();
        room.setId(90);
        assertThrows(RoomsException.class, () ->{new RoomsManager().add(room);});
    }

    @Test
    void addWithWrongPar(){
        RoomsManager manager = new RoomsManager();
        Rooms room = new Rooms();
        room.setRoomTypeID(90);
        assertThrows(RoomsException.class, () ->{new RoomsManager().add(room);});
    }


}
