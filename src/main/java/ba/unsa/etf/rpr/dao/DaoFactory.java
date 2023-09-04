package ba.unsa.etf.rpr.dao;

/**
 * Factory for all DAO classes
 * @author Aldin Islamagic
 */

public class DaoFactory {

    private static final GuestsDao guestsDao = GuestsDaoSQLImpl.getInstance();

    private static final ReservationsDao reservationsDao = ReservationsDaoSQLImpl.getInstance();

    private static final RoomsDao roomsDao = RoomsDaoSQLImpl.getInstance();

    private static final RoomTypesDao roomTypes = RoomTypesDaoSQLImpl.getInstance();
}
