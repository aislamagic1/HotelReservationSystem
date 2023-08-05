package ba.unsa.etf.rpr.dao;

import java.util.List;

/**
 * Root interface for all DAO classes
 *
 * @author Aldin Islamagic
 */

public interface Dao<T> {

    /**
     * Get entity from database based on ID
     * @param id primary key of entity
     * @return Entity from database
     */
    T getById(int id);

    /**
     * Saves given entity in database
     * @param item item to save in database
     * @return saved item with id field populated
     */
    T add(T item);

    /**
     * Fully updates entity in database based on id (primary) match.
     * @param item - item to be updated
     * @return updated version of given item
     */
    T update(T item);

    /**
     * Delete item from database with given id
     * @param id - primary key of entity
     */
    void delete(int id);

    /**
     * Lists all entities from database.
     * @return List of entities from database
     */
    List<T> getAll();
}
