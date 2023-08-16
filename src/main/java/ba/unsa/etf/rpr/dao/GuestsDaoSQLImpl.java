package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Guests;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class GuestsDaoSQLImpl extends AbstractDao<Guests> implements GuestsDao{

    public GuestsDaoSQLImpl() {
        super("Guests");
    }


    @Override
    public Guests row2object(ResultSet rs){
        try {
            Guests guest = new Guests();
            guest.setId(rs.getInt("Guest_id"));
            guest.setFirstName(rs.getString("First_name"));
            guest.setLastName(rs.getString("Last_name"));
            guest.setEmail(rs.getString("eMail"));
            guest.setPassword(rs.getString("password"));
            return guest;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Map<String, Object> object2row(Guests object) {
        return null;
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
                guest.setId(rs.getInt("Guest_id"));
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
