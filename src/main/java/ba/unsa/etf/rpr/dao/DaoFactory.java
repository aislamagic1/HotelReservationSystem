package ba.unsa.etf.rpr.dao;

/**
 * Factory for all DAO classes
 * @author Aldin Islamagic
 */

public class DaoFactory {

    private static final GuestsDao guestsDao = new GuestsDaoSQLImpl();

    private static final ReservationsDao reservationsDao = new ReservationsDaoSQLImpl();

    private static final RoomsDao roomsDao = new RoomsDaoSQLImpl();

    private static final RoomTypesDao roomTypes = new RoomTypesDaoSQLImpl();
}
