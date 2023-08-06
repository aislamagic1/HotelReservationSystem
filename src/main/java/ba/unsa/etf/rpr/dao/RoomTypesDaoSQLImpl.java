package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.RoomTypes;
import ba.unsa.etf.rpr.domain.Rooms;

import java.io.FileInputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class RoomTypesDaoSQLImpl implements RoomTypesDao{

    private Connection connection;

    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     */
    public RoomTypesDaoSQLImpl(){
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
    public RoomTypes getById(int id) {
        String query = "SELECT * FROM Room_Types WHERE Room_type_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                RoomTypes roomType = new RoomTypes();
                roomType.setId(rs.getInt("Room_type_id"));
                roomType.setRoomType(rs.getString("Room_type"));
                roomType.setNumPersons(rs.getInt("Num_persons"));
                roomType.setRoomPrice(rs.getDouble("Room_price"));
                rs.close();
                return roomType;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RoomTypes add(RoomTypes item) {
        String insert = "INSERT INTO Room_Types (Room_type, Num_persons, Room_price) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getRoomType());
            stmt.setInt(2, item.getNumPersons());
            stmt.setDouble(3, item.getRoomPrice());
            stmt.executeUpdate();
            return item;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RoomTypes update(RoomTypes item) {
        String update = "UPDATE Room_Types SET Room_type = ?, Num_persons = ?, Room_price = ? WHERE Room_type_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getRoomType());
            stmt.setInt(2, item.getNumPersons());
            stmt.setDouble(3, item.getRoomPrice());
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

    }

    @Override
    public List<RoomTypes> getAll() {
        return null;
    }
}
