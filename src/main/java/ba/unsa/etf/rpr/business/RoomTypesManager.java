package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.dao.RoomTypesDao;
import ba.unsa.etf.rpr.domain.RoomTypes;
import ba.unsa.etf.rpr.domain.Rooms;
import ba.unsa.etf.rpr.exception.ReservationsException;
import ba.unsa.etf.rpr.exception.RoomTypesException;
import ba.unsa.etf.rpr.exception.RoomsException;

import java.util.List;

/**
 * Business logic layer for RoomTypesManager
 * @author Aldin Islamagic
 */

public class RoomTypesManager {

    /**
     * Get all room types from db
     * @return list of room types
     */
    public List<RoomTypes> getAll(){
        return DaoFactory.roomTypesDao().getAll();
    }

    /**
     * Delete instance from db
     * @param id of object
     * @throws RoomTypesException
     */
    public void delete(int id) throws RoomTypesException {
        try{
            DaoFactory.roomTypesDao().delete(id);
        }catch(Exception e){
            throw new RoomTypesException("Greska prilikom brisanja");
        }
    }

    /**
     * Method adds item in db
     * @param item to be added
     * @throws RoomTypesException
     */

    public void add(RoomTypes item) throws RoomTypesException {
        if(item.getId() != 0) throw new RoomTypesException("Id must be autoincrement");
        try{
            DaoFactory.roomTypesDao().add(item);
        }catch(Exception e){
            throw new RoomTypesException("Greska prilikom brisanja");
        }
    }


    /**
     * Get item by id
     * @param id of item
     * @return item from db
     * @throws RoomTypesException
     */
    public RoomTypes getById(int id) throws RoomTypesException{
        try{
            return DaoFactory.roomTypesDao().getById(id);
        }
        catch(Exception e){
            throw new RoomTypesException("Tip sobe sa id-om ne postoji!");
        }
    }

    /**
     * Udpate item id db
     * @param item to be updated
     * @return updated item
     * @throws RoomTypesException
     */
    public RoomTypes update(RoomTypes item) throws RoomTypesException {
        try{
            return DaoFactory.roomTypesDao().update(item);
        }
        catch(Exception e){
            throw new RoomTypesException("Tip sobe ne postoji ili podaci nisu validni!");
        }
    }
}
