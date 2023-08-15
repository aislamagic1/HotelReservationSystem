package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


/**
 * Abstract class that implements core DAO CRUD methods for every entity
 * @author Aldin Islamagic
 */

public abstract class AbstractDao<T extends Idable> implements Dao<T> {
    private Connection connection;
    private String tableName;

    /**
     * Constructor that makes connection to the database with  hidden values needed to make the connection
     */
    public AbstractDao(String tableName){
        try{
            this.tableName = tableName;
            String fieldPath = "src/dataBase.properties";
            Properties pros = new Properties();
            FileInputStream ip = new FileInputStream(fieldPath);
            pros.load(ip);
            this.connection = DriverManager.getConnection(pros.getProperty("url"), pros.getProperty("username"), pros.getProperty("password"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method that gets connection from db
     * @return connection of the db
     */
    public Connection getConnection() {return this.connection; }

}
