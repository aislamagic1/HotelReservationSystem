package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.dao.GuestsDao;
import ba.unsa.etf.rpr.domain.Guests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class GuestManagerTest {

    @Mock
    GuestsDao guestDao;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    public Guests createGuest(){
        Guests guest = new Guests();
        guest.setFirstName("TestIme");
        guest.setLastName("TestPrezime");
        guest.setEmail("Email");
        guest.setPassword("mock");
        return guest;
    }

    @Test
    public void test1(){
        Mockito.when(guestDao.getById(1)).thenReturn(createGuest());
        Guests g = guestDao.getById(1);

        assert(g.getFirstName().equals("TestIme"));
        assert(g.getLastName().equals("TestPrezime"));
        assert(g.getEmail().equals("Email"));
        assert(g.getPassword().equals("mock"));
    }

    @Test
    public void test2(){
        GuestsDao guestDaoSpy = Mockito.spy(guestDao);
        Mockito.when(guestDaoSpy.getById(Mockito.anyInt())).thenReturn(createGuest());
        Guests g = guestDaoSpy.getById(100);
        System.out.println(g.getFirstName());
        System.out.println(g.getLastName());
    }


}
