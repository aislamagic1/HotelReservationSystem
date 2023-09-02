package ba.unsa.etf.rpr.controllers;

import javafx.scene.control.Label;

public class ReservationInfoController {
    public Label roomNumber;
    public Label roomType;
    public Label arrivalDate;
    public Label checkOutDate;

    public void displayRoomNumber(String val){
        roomNumber.setText(val);
    }

    public void displayRoomType(String val){
        roomType.setText(val);
    }

    public void displayArrivalDate(String val){
        arrivalDate.setText(val);
    }

    public void displayCheckOutDate(String val){
        checkOutDate.setText(val);
    }

}
