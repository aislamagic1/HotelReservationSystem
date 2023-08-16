package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.RoomTypes;
import ba.unsa.etf.rpr.domain.Rooms;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class RoomTypesDaoSQLImpl extends AbstractDao<RoomTypes> implements RoomTypesDao{


    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     */
    public RoomTypesDaoSQLImpl() {
        super("Room_Types");
    }

    @Override
    public RoomTypes row2object(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Object> object2row(RoomTypes object) {
        return null;
    }
}
