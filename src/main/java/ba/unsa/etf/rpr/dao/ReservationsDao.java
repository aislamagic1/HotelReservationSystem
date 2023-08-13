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

    /**
     * Method finds all arrival and check out dates from database that belong to a
     * reservation to all room's with the given room type
     * @param roomTypeId id of the room type
     * @return list of arrival and check out dates represented with pairs
     */
    public List<Pair<Date, Date>> getAllSchedulesForRooms(int roomTypeId);
}
