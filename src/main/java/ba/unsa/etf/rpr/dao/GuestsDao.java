package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Guests;

/**
 * Dao interface for Guests domain bean
 *
 * @author Aldin Islamagic
 */

public interface GuestsDao extends Dao<Guests>{

    /**
     * Method that finds guest from db with given email and password
     * @param email email of guest account
     * @param password password of guest account
     * @return guest object
     */
    public Guests getGuestByEmailAndPassword(String email, String password);
}
