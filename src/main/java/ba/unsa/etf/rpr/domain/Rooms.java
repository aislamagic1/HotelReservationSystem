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
    private Reservations reservation;
    private RoomTypes roomType;

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

    public Reservations getReservation() {
        return reservation;
    }

    public void setReservation(Reservations reservation) {
        this.reservation = reservation;
    }

    public RoomTypes getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypes roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "id=" + id +
                ", occupancy=" + occupancy +
                ", reservation=" + reservation +
                ", roomType=" + roomType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rooms rooms = (Rooms) o;
        return id == rooms.id && Objects.equals(occupancy, rooms.occupancy) && reservation.equals(rooms.reservation) && roomType.equals(rooms.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, occupancy, reservation, roomType);
    }
}
