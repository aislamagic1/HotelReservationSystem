package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * Bean for room
 *
 * @author Aldin Islamagic
 */

public class Rooms {
    private int id;
    private int roomTypeID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }


    @Override
    public String toString() {
        return "Rooms{" +
                "id=" + id +
                ", roomTypeID=" + roomTypeID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rooms rooms = (Rooms) o;
        return id == rooms.id && roomTypeID == rooms.roomTypeID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomTypeID);
    }
}
