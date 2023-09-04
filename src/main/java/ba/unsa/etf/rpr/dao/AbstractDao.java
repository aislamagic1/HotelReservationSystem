package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;
import ba.unsa.etf.rpr.domain.Reservations;

import java.io.FileInputStream;
import java.io.FileReader;
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
            FileReader fileReader = new FileReader("src/dataBase.properties");
            Properties property = new Properties();
            property.load(fileReader);
            this.connection = DriverManager.getConnection(property.getProperty("url"), property.getProperty("username"), property.getProperty("password"));
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
     * Method that gets all objects from table in db
     * @return list of object
     */
    public List<T> getAll(){
        return executeQuery("SELECT * FROM "+ this.tableName, null);
    }

    /**
     * Method that deletes object with given id
     * @param id - primary key of entity
     */
    public void delete(int id){
        String sql = "DELETE FROM "+ this.tableName +" WHERE id = ?";
        try{
            PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that adds object into database
     * @param item item to save in database
     * @return object that is added into database
     */
    public T add(T item){
        Map<String, Object> row = object2row(item);
        Map.Entry<String, String> columns = prepareInsertParts(row);

        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(tableName);
        builder.append(" (").append(columns.getKey()).append(") ");
        builder.append("VALUES (").append(columns.getValue()).append(")");

        try{
            PreparedStatement stmt = getConnection().prepareStatement(builder.toString(), Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            for (Map.Entry<String, Object> entry: row.entrySet()) {
                if (entry.getKey().equals("id")) continue;
                stmt.setObject(counter, entry.getValue());
                counter++;
            }
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            item.setId(rs.getInt(1));
            return item;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public T update(T item){
        Map<String, Object> row = object2row(item);
        String updateColumns = prepareUpdateParts(row);
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(tableName).append(" SET ").append(updateColumns).append(" WHERE id = ?");
        try {
            PreparedStatement stmt = getConnection().prepareStatement(builder.toString());
            int counter = 1;
            for(Map.Entry<String, Object> entry : row.entrySet()){
                if(entry.getKey().equals("id")) continue;
                stmt.setObject(counter, entry.getValue());
                counter++;
            }
            stmt.setObject(counter, item.getId());
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * Method that helps prepare names of columns and returns comma seperated values of columns and question marks for add method
     * @param row - map of objects
     * @return - map entry
     */
    private Map.Entry<String, String> prepareInsertParts(Map<String, Object> row){
        StringBuilder columns = new StringBuilder();
        StringBuilder questions = new StringBuilder();

        int counter = 0;
        boolean foundId = false;
        for(Map.Entry<String, Object> entry : row.entrySet()){
            counter++;
            if(entry.getKey().equals("id")) {
                foundId = true;
                continue;
            }
            columns.append(entry.getKey());
            questions.append("?");
            if(row.size() - 1 == counter && !foundId) break; // for case if "id" is in last place of row object
            if(row.size() != counter){
                columns.append(",");
                questions.append(",");
            }
        }
        return new AbstractMap.SimpleEntry<>(columns.toString(), questions.toString());
    }

    /**
     * Method that prepares string for update statement
     * @param row - row that gets converted into string
     * @return string used in update statement
     */
    private String prepareUpdateParts(Map<String, Object> row){
        StringBuilder columns = new StringBuilder();

        int counter = 0;
        for(Map.Entry<String , Object> entry : row.entrySet()){
            counter++;
            if(entry.getKey().equals("id")) continue;
            columns.append(entry.getKey()).append("= ?");
            if(row.size() != counter){
                columns.append(",");
            }
        }
        return columns.toString();
    }

}
