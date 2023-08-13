package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Reservations;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReservationsDaoSQLImpl implements ReservationsDao{

    private Connection connection;

    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     */
    public ReservationsDaoSQLImpl(){
        try{
            String fieldPath = "src/dataBase.properties";
            Properties pros = new Properties();
            FileInputStream ip = new FileInputStream(fieldPath);
            pros.load(ip);
            this.connection = DriverManager.getConnection(pros.getProperty("url"), pros.getProperty("username"), pros.getProperty("password"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Reservations getById(int id) {
        String query = "SELECT * FROM reservations WHERE Reservation_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Reservations reservation = new Reservations();
                reservation.setId(rs.getInt("Reservation_id"));
                reservation.setArrivalDate(rs.getDate("Arrival_date"));
                reservation.setCheckOutDate(rs.getDate("Check_out_date"));
                reservation.setGuest(rs.getInt("Guest_id"));
                reservation.setRoom_id(rs.getInt("Room_id"));
                rs.close();
                return reservation;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reservations add(Reservations item) {
        String insert = "INSERT INTO Reservations (Arrival_date, Check_out_date, Guest_id, Room_id) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, (Date) item.getArrivalDate());
            stmt.setDate(2, (Date) item.getCheckOutDate());
            stmt.setInt(3, item.getGuest());
            stmt.setInt(4, item.getRoom_id());
            stmt.executeUpdate();
            return item;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reservations update(Reservations item) {
        String update = "UPDATE Reservations SET Arrival_date = ?, Check_out_date = ?, Guest_id = ?, Room_id = ? WHERE Reservation_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, (Date) item.getArrivalDate());
            stmt.setDate(2, (Date) item.getCheckOutDate());
            stmt.setInt(3, item.getGuest());
            stmt.setInt(4, item.getRoom_id());
            stmt.setInt(5, item.getId());
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Reservations WHERE Reservation_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reservations> getAll() {
        String query = "SELECT * FROM Reservations";
        List<Reservations> reservations = new ArrayList<Reservations>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Reservations reservation = new Reservations();
                reservation.setId(rs.getInt("Reservation_id"));
                reservation.setArrivalDate(rs.getDate("Arrival_date"));
                reservation.setCheckOutDate(rs.getDate("Check_out_date"));
                reservation.setGuest(rs.getInt("Guest_id"));
                reservation.setRoom_id(rs.getInt("Room_id"));
                reservations.add(reservation);
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reservations;
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
            PreparedStatement stmt = this.connection.prepareStatement(query);
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
