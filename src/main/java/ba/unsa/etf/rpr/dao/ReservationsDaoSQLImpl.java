package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Reservations;

import java.io.FileInputStream;
import java.sql.*;
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
        String insert = "INSERT INTO Reservations (Arrival_date, Check_out_date, Guest_id) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, (Date) item.getArrivalDate());
            stmt.setDate(2, (Date) item.getCheckOutDate());
            stmt.setInt(3, item.getGuest());
            stmt.executeUpdate();
            return item;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reservations update(Reservations item) {
        String update = "UPDATE Guests SET Arrival_date = ?, Check_out_date = ?, Guest_id = ? WHERE Reservation_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, (Date) item.getArrivalDate());
            stmt.setDate(2, (Date) item.getCheckOutDate());
            stmt.setInt(3, item.getGuest());
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Reservations> getAll() {
        return null;
    }
}
