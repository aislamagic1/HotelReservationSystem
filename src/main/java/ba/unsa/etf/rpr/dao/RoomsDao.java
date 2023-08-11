package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Rooms;

import java.util.List;

/**
 * Dao interface for Rooms domain bean
 *
 * @author Aldin Islamagic
 */

public interface RoomsDao extends Dao<Rooms>{

    /**
     * Method finds every room that is a specific type
     * @param roomTypeId id of the room type
     * @return list of the rooms
     */
    public List<Rooms> getRoomsWithSameRoomType(int roomTypeId);
}
