package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Guests;
import jdk.jfr.Category;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class GuestsDaoSQLImpl implements GuestsDao{

    private Connection connection;


    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     */
    public GuestsDaoSQLImpl(){
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
    public Guests getById(int id) {
        String query = "SELECT * FROM Guests WHERE Guest_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Guests guest = new Guests();
                guest.setId(rs.getInt("Guest_id"));
                guest.setFirstName(rs.getString("First_name"));
                guest.setLastName(rs.getString("Last_name"));
                guest.setEmail(rs.getString("eMail"));
                guest.setPassword(rs.getString("password"));
                rs.close();
                return guest;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Guests add(Guests item) {
        String insert = "INSERT INTO Guests (First_name, Last_name, eMail, password) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getFirstName());
            stmt.setString(2, item.getLastName());
            stmt.setString(3, item.getEmail());
            stmt.setString(4, item.getPassword());
            stmt.executeUpdate();
            return item;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Guests update(Guests item) {
        String update = "UPDATE Guests SET First_name = ?, Last_name = ?, eMail = ?, password = ? WHERE Guest_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getFirstName());
            stmt.setString(2, item.getLastName());
            stmt.setString(3, item.getEmail());
            stmt.setString(4, item.getPassword());
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
        String query = "DELETE FROM Guests WHERE Guest_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Guests> getAll() {
        String query = "SELECT * FROM Guests";
        List<Guests> guests = new ArrayList<Guests>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Guests guest = new Guests();
                guest.setId(rs.getInt("Guest_id"));
                guest.setFirstName(rs.getString("First_name"));
                guest.setLastName(rs.getString("Last_name"));
                guest.setEmail(rs.getString("eMail"));
                guest.setPassword(rs.getString("password"));
                guests.add(guest);
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return guests;
    }
}
