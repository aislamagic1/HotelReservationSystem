package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Guests;
import ba.unsa.etf.rpr.domain.Reservations;
import ba.unsa.etf.rpr.exception.GuestException;
import ba.unsa.etf.rpr.exception.ReservationsException;

import java.util.List;

/**
 * Business logic layer for ReservationsManager
 * @author Aldin Islamagic
 */

public class ReservationsManager {

    /**
     * Get all reservations from db
     * @return list of reservations
     */
    public List<Reservations> getAll(){
        return DaoFactory.reservationsDao().getAll();
    }

    /**
     * Delete instance from db
     * @param id of object
     * @throws ReservationsException
     */
    public void delete(int id) throws ReservationsException {
        try{
            DaoFactory.reservationsDao().delete(id);
        }catch(Exception e){
            throw new ReservationsException("Greska prilikom brisanja");
        }
    }

    /**
     * Method adds item in db
     * @param item to be added
     * @throws ReservationsException
     */

    public void add(Reservations item) throws ReservationsException {
        if(item.getId() != 0) throw new ReservationsException("Id must be autoincrement");
        try{
            DaoFactory.reservationsDao().add(item);
        }catch(Exception e){
            throw new RuntimeException("Greska prilikom brisanja");
        }
    }


    /**
     * Get item by id
     * @param id of item
     * @return item from db
     * @throws GuestException
     */
    public Reservations getById(int id) throws ReservationsException {
        try{
            return DaoFactory.reservationsDao().getById(id);
        }
        catch(Exception e){
            throw new ReservationsException("Rezervacija sa id-om ne postoji!");
        }
    }

    /**
     * Udpate item id db
     * @param item to be updated
     * @return updated item
     * @throws Reservations
     */
    public Reservations update(Reservations item) throws ReservationsException {
        try{
            return DaoFactory.reservationsDao().update(item);
        }
        catch(Exception e){
            throw new ReservationsException("Rezervacija ne postoji ili podaci nisu validni!");
        }
    }
}
