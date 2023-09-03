package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.RoomTypes;
import ba.unsa.etf.rpr.domain.Rooms;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class RoomTypesDaoSQLImpl extends AbstractDao<RoomTypes> implements RoomTypesDao{


    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     */
    public RoomTypesDaoSQLImpl() {
        super("Room_Types");
    }

    /**
     * Method that sets parems. of room from given attribute
     * @param rs result set from database
     * @return room_type object
     */
    @Override
    public RoomTypes row2object(ResultSet rs) throws SQLException {
        try {
            RoomTypes roomType = new RoomTypes();
            roomType.setId(rs.getInt("id"));
            roomType.setRoomType(rs.getString("Room_Type"));
            roomType.setNumPersons(rs.getInt("Num_persons"));
            roomType.setRoomPrice(rs.getDouble("Room_price"));
            return roomType;
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
    public Map<String, Object> object2row(RoomTypes object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("Room_type", object.getRoomType());
        row.put("Num_persons", object.getNumPersons());
        row.put("Room_price", object.getRoomPrice());
        return row;
    }
}
