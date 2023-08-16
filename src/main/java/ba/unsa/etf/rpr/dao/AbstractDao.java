package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;
import ba.unsa.etf.rpr.domain.Reservations;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;


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

    /**
     * Method for mapping ResultSet into Object
     * @param rs result set from database
     * @return a bean object for a specific table
     */
    public abstract T row2object(ResultSet rs) throws SQLException;


    /**
     * Method for maping Object into Map
     * @param object a bean object for a specific table
     * @return value of object in a sorted map
     */
    public abstract Map<String, Object> object2row(T object);

    /**
     * Method that searches object by given id
     * @param id primary key of entity
     * @return object with given id
     */
    public T getById(int id){
        return  executeQueryUnique("SELECT * FROM " + this.tableName + " WHERE id = ?", new Object[]{id});
    }


    /**
     * Method that executes any kind of query
     * @param query query to be executed
     * @param params parameters for query
     * @return list of results of the given query
     */
    public List<T> executeQuery(String query, Object[] params){
        try{
            PreparedStatement stmt = getConnection().prepareStatement(query);
            if(params != null){
                for(int i = 1; i <= params.length; i++){
                    stmt.setObject(i, params[i-1]);
                }
            }
            ResultSet rs = stmt.executeQuery();
            ArrayList<T> resultList = new ArrayList<>();
            while(rs.next()){
                resultList.add(row2object(rs));
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that executes query that witch result is only one object
     * @param query query to be executed
     * @param params parameters for query
     * @return result of the query
     */
    public T executeQueryUnique(String query, Object[] params){
        List<T> result = executeQuery(query, params);
        if(result != null && result.size() == 1){
            return result.get(0);
        }else{
            return null;
        }
    }

}
