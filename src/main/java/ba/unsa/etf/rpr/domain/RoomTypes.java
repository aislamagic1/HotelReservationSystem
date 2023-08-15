package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * List of types of rooms
 *
 * @author Aldin Islamagic
 */

public class RoomTypes implements Idable{
    private int id;
    private String roomType;
    private int numPersons;
    private double roomPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    @Override
    public String toString() {
        return "RoomTypes{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", numPersons=" + numPersons +
                ", roomPrice=" + roomPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomTypes roomTypes = (RoomTypes) o;
        return id == roomTypes.id && numPersons == roomTypes.numPersons && Double.compare(roomTypes.roomPrice, roomPrice) == 0 && Objects.equals(roomType, roomTypes.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomType, numPersons, roomPrice);
    }
}
