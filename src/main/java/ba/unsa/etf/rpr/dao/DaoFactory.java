package ba.unsa.etf.rpr.dao;

import java.util.Locale;

/**
 * Factory for all DAO classes
 * @author Aldin Islamagic
 */

public class DaoFactory {

    private static final GuestsDao guestsDao = GuestsDaoSQLImpl.getInstance();

    private static final ReservationsDao reservationsDao = ReservationsDaoSQLImpl.getInstance();

    private static final RoomsDao roomsDao = RoomsDaoSQLImpl.getInstance();

    private static final RoomTypesDao roomTypes = RoomTypesDaoSQLImpl.getInstance();

    private DaoFactory(){}

    public static GuestsDao guestsDao(){
        return guestsDao;
    }

    public static ReservationsDao reservationsDao(){
        return reservationsDao;
    }

    public static RoomsDao roomsDao(){
        return roomsDao;
    }

    public static RoomTypesDao roomTypesDao(){
        return roomTypes;
    }
}
