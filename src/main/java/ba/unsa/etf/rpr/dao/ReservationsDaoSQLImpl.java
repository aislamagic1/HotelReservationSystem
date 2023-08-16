package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Reservations;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ReservationsDaoSQLImpl extends AbstractDao<Reservations> implements ReservationsDao{

    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     */
    public ReservationsDaoSQLImpl() {
        super("Reservations");
    }

    @Override
    public Reservations row2object(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Object> object2row(Reservations object) {
        return null;
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
}
