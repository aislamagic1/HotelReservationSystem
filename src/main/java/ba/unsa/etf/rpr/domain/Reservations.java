package ba.unsa.etf.rpr.domain;

import java.util.Date;
import java.util.Objects;

/**
 * List of reservations that the guest made
 *
 * @author Aldin Islamagic
 */

public class Reservations {
    private int id;
    private Date arrivalDate;
    private Date checkOutDate;
    private int guestId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getGuest() {
        return guestId;
    }

    public void setGuest(int guestId) {
        this.guestId = guestId;
    }


    @Override
    public String toString() {
        return "Reservations{" +
                "id=" + id +
                ", arrivalDate=" + arrivalDate +
                ", checkOutDate=" + checkOutDate +
                ", guestId=" + guestId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservations that = (Reservations) o;
        return id == that.id && guestId == that.guestId && Objects.equals(arrivalDate, that.arrivalDate) && Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, arrivalDate, checkOutDate, guestId);
    }
}
