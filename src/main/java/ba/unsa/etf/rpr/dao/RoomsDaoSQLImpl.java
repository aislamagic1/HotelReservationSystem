package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Reservations;
import ba.unsa.etf.rpr.domain.Rooms;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class RoomsDaoSQLImpl extends AbstractDao<Rooms> implements RoomsDao{


    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     *
     */
    public RoomsDaoSQLImpl() {
        super("Rooms");
    }

    /**
     * Method that sets parems. of room from given attribute
     * @param rs result set from database
     * @return room object
     */
    @Override
    public Rooms row2object(ResultSet rs) throws SQLException {
        try {
            Rooms room = new Rooms();
            room.setId(rs.getInt("Room_id"));
            room.setRoomTypeID(rs.getInt("Room_type_id"));
            return room;
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
    public Map<String, Object> object2row(Rooms object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("Room_id", object.getId());
        row.put("Room_type_id", object.getRoomTypeID());
        return row;
    }

    @Override
    public List<Rooms> getRoomsWithSameRoomType(int roomTypeId) {
        String query = "Select * From Rooms WHERE Room_type_id = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1,roomTypeId);
            ResultSet rs = stmt.executeQuery();
            List<Rooms> listOfRooms = new ArrayList<>();
            while(rs.next()){
                Rooms room = new Rooms();
                room.setId(rs.getInt("Room_id"));
                room.setRoomTypeID(rs.getInt("Room_type_id"));
                listOfRooms.add(room);
            }
            return listOfRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
