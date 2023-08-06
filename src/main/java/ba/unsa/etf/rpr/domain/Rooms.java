package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * Bean for room
 *
 * @author Aldin Islamagic
 */

public class Rooms {
    private int id;
    private Integer occupancy;
    private int reservationId;
    private int roomTypeID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
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
                ", occupancy=" + occupancy +
                ", reservationId=" + reservationId +
                ", roomTypeID=" + roomTypeID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rooms rooms = (Rooms) o;
        return id == rooms.id && reservationId == rooms.reservationId && roomTypeID == rooms.roomTypeID && Objects.equals(occupancy, rooms.occupancy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, occupancy, reservationId, roomTypeID);
    }
}
