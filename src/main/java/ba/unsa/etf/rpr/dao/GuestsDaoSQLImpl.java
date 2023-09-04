package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Guests;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class GuestsDaoSQLImpl extends AbstractDao<Guests> implements GuestsDao{

    private static GuestsDaoSQLImpl instance = null;

    private GuestsDaoSQLImpl() {
        super("Guests");
    }

    public static GuestsDaoSQLImpl getInstance(){
        if(instance == null){
            instance = new GuestsDaoSQLImpl();
        }
        return instance;
    }

    public static void removeInstance(){
        if(instance != null)
            instance = null;
    }


    /**
     * Method that sets parems. of guest from given attribute
     * @param rs result set from database
     * @return guest object
     */
    @Override
    public Guests row2object(ResultSet rs){
        try {
            Guests guest = new Guests();
            guest.setId(rs.getInt("id"));
            guest.setFirstName(rs.getString("First_name"));
            guest.setLastName(rs.getString("Last_name"));
            guest.setEmail(rs.getString("eMail"));
            guest.setPassword(rs.getString("password"));
            return guest;
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
    public Map<String, Object> object2row(Guests object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("First_name", object.getFirstName());
        row.put("Last_name", object.getLastName());
        row.put("eMail", object.getEmail());
        row.put("password", object.getPassword());
        return row;
    }


    /**
     * Method that finds guest with given email and password
     * @param email email of guest
     * @param password password of guest
     * @return guest object with given eamil and password
     */
    @Override
    public Guests getGuestByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM Guests WHERE eMail = ? AND password = ?";
        try{
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            Guests guest = new Guests();
            while(rs.next()){
                guest.setId(rs.getInt("id"));
                guest.setFirstName(rs.getString("First_name"));
                guest.setLastName(rs.getString("Last_name"));
                guest.setEmail(rs.getString("eMail"));
                guest.setPassword(rs.getString("password"));
            }
            rs.close();
            return guest;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
