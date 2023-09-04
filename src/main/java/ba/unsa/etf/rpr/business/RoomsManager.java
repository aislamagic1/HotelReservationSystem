package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Reservations;
import ba.unsa.etf.rpr.domain.RoomTypes;
import ba.unsa.etf.rpr.domain.Rooms;
import ba.unsa.etf.rpr.exception.GuestException;
import ba.unsa.etf.rpr.exception.ReservationsException;
import ba.unsa.etf.rpr.exception.RoomsException;

import java.util.List;

/**
 * Business logic layer for RoomsManager
 * @author Aldin Islamagic
 */

public class RoomsManager {
    /**
     * Get all rooms from db
     * @return list of rooms
     */
    public List<Rooms> getAll(){
        return DaoFactory.roomsDao().getAll();
    }

    /**
     * Delete instance from db
     * @param id of object
     * @throws RoomsException
     */
    public void delete(int id) throws RoomsException {
        try{
            DaoFactory.roomsDao().delete(id);
        }catch(Exception e){
            throw new RoomsException("Greska prilikom brisanja");
        }
    }

    /**
     * Method adds item in db
     * @param item to be added
     * @throws ReservationsException
     */

    public void add(Rooms item) throws RoomsException {
        if(item.getId() != 0) throw new RoomsException("Id must be autoincrement");
        List<RoomTypes> roomTypes = DaoFactory.roomTypesDao().getAll();
        boolean hasId = false;
        for(RoomTypes x : roomTypes){
            if(x.getId() == item.getRoomTypeID()) hasId = true;
        }
        if(!hasId) throw new RoomsException("Id of room type does not exist");
        try{
            DaoFactory.roomsDao().add(item);
        }catch(Exception e){
            throw new RoomsException("Greska prilikom brisanja");
        }
    }


    /**
     * Get item by id
     * @param id of item
     * @return item from db
     * @throws RoomsException
     */
    public Rooms getById(int id) throws RoomsException{
        try{
            return DaoFactory.roomsDao().getById(id);
        }
        catch(Exception e){
            throw new RoomsException("Soba sa id-om ne postoji!");
        }
    }

    /**
     * Udpate item id db
     * @param item to be updated
     * @return updated item
     * @throws RoomsException
     */
    public Rooms update(Rooms item) throws RoomsException {
        try{
            return DaoFactory.roomsDao().update(item);
        }
        catch(Exception e){
            throw new RoomsException("Soba ne postoji ili podaci nisu validni!");
        }
    }

    /**
     * Method finds every room that is a specific type
     * @param roomTypeId id of the room type
     * @return list of the rooms
     */
    public List<Rooms> getRoomsWithSameRoomType(int roomTypeId){
        return DaoFactory.roomsDao().getRoomsWithSameRoomType(roomTypeId);
    }

}
