package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;


/**
 * Abstract class that implements core DAO CRUD methods for every entity
 * @author Aldin Islamagic
 */

public abstract class AbstractDao<T extends Idable> implements Dao<T> {

}
