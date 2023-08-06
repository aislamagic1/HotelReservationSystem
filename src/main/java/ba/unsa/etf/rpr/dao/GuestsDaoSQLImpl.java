package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Guests;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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
        return null;
    }

    @Override
    public Guests add(Guests item) {
        return null;
    }

    @Override
    public Guests update(Guests item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Guests> getAll() {
        return null;
    }
}
