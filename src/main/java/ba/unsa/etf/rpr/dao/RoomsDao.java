package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Rooms;

import java.util.List;

/**
 * Dao interface for Rooms domain bean
 *
 * @author Aldin Islamagic
 */

public interface RoomsDao extends Dao<Rooms>{

    public List<Rooms> getRoomsWithSameRoomType(int roomTypeId);
}
