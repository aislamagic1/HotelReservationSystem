package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Reservations;
import javafx.util.Pair;


import java.util.Date;
import java.util.List;

/**
 * Dao interface for Reservations domain bean
 *
 * @author Aldin Islamagic
 */

public interface ReservationsDao extends Dao<Reservations>{

    public List<Pair<String, Integer>> getAllSchedulesForRooms(int roomTypeId);
}
