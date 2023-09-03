package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Guests;
import ba.unsa.etf.rpr.domain.Reservations;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ReservationsDaoSQLImpl extends AbstractDao<Reservations> implements ReservationsDao{

    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     */
    public ReservationsDaoSQLImpl() {
        super("Reservations");
    }

    /**
     * Method that sets parems. of guest from given attribute
     * @param rs result set from database
     * @return reservation object
     */
    @Override
    public Reservations row2object(ResultSet rs) throws SQLException {
        try {
            Reservations reservation = new Reservations();
            reservation.setId(rs.getInt("id"));
            reservation.setArrivalDate(rs.getDate("Arrival_date"));
            reservation.setCheckOutDate(rs.getDate("Check_out_date"));
            reservation.setGuest(rs.getInt("Guest_id"));
            reservation.setRoom_id(rs.getInt("Room_id"));
            return reservation;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that turns given object to sql query
     * @param object a bean object for a specific table
     * @return Map of object
     */
    @Override
    public Map<String, Object> object2row(Reservations object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("Arrival_date", object.getArrivalDate());
        row.put("Check_out_date", object.getCheckOutDate());
        row.put("Guest_id", object.getGuest());
        row.put("Room_id", object.getRoom_id());
        return row;
    }

    @Override
    public List<Pair<LocalDate, LocalDate>> getAllSchedulesForRooms(int roomTypeId) {
        String query = "SELECT r.Arrival_date, r.Check_out_date " +
                "FROM freedb_Hotel_Reservation_System.Reservations r " +
                "WHERE r.Room_id IN ( " +
                "SELECT r2.Room_id " +
                "FROM freedb_Hotel_Reservation_System.Rooms r2 " +
                "WHERE r2.Room_type_id = ?" +
                ")";
        List<Pair<LocalDate, LocalDate>> result = new ArrayList<>();
        try{
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, roomTypeId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                result.add(new Pair<>(rs.getDate("Arrival_date").toLocalDate(), rs.getDate("Check_out_date").toLocalDate()));
            }
            rs.close();
            return result;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Reservations> getAllReservationsForGuest(Integer guestId) {
        return executeQuery("SELECT * FROM Reservations WHERE Guest_id = ?", new Object[]{guestId});
    }
}
