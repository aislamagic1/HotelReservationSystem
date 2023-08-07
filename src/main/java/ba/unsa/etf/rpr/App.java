package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.dao.GuestsDao;
import ba.unsa.etf.rpr.dao.GuestsDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Guests;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Guests guest = new Guests();
        guest.setFirstName("test2");
        guest.setLastName("test2");
        guest.setEmail("hello");
        guest.setPassword("123456");
        GuestsDao dao = new GuestsDaoSQLImpl();
        dao.add(guest);
//        dao.delete(7);
        List<Guests> guests = dao.getAll();
        System.out.println(guests);
    }
}
