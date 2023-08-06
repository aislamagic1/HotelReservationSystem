package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Reservations;
import ba.unsa.etf.rpr.domain.Rooms;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RoomsDaoSQLImpl implements RoomsDao{

    private Connection connection;

    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     */
    public RoomsDaoSQLImpl(){
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
    public Rooms getById(int id) {
        String query = "SELECT * FROM Rooms WHERE Room_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Rooms room = new Rooms();
                room.setId(rs.getInt("Room_id"));
                room.setOccupancy(rs.getInt("Occupancy"));
                room.setReservationId(rs.getInt("Reservations_id"));
                room.setRoomTypeID(rs.getInt("Room_type_id"));
                rs.close();
                return room;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Rooms add(Rooms item) {
        String insert = "INSERT INTO Rooms (Occupancy, Reservations_id, Room_type_id) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, item.getOccupancy());
            stmt.setInt(2, item.getReservationId());
            stmt.setInt(3, item.getRoomTypeID());
            stmt.executeUpdate();
            return item;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Rooms update(Rooms item) {
        String update = "UPDATE Rooms SET Occupancy = ?, Reservations_id = ?, Room_type_id = ? WHERE Room_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, item.getOccupancy());
            stmt.setInt(2, item.getReservationId());
            stmt.setInt(3, item.getRoomTypeID());
            stmt.setInt(4, item.getId());
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Rooms WHERE Room_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Rooms> getAll() {
        String query = "SELECT * FROM Rooms";
        List<Rooms> rooms = new ArrayList<Rooms>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Rooms room = new Rooms();
                room.setId(rs.getInt("Room_id"));
                room.setOccupancy(rs.getInt("Occupancy"));
                room.setReservationId(rs.getInt("Reservations_id"));
                room.setRoomTypeID(rs.getInt("Room_type_id"));
                rooms.add(room);
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rooms;
    }
}
