package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Reservations;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ReservationInfoController {
    public Label roomNumber;
    public Label roomType;
    public Label arrivalDate;
    public Label checkOutDate;
    public Button closeButton;

    private Reservations reservation;

    public void setReservationAndLabels(Reservations val){
        reservation = val;
        roomNumber.setText(String.valueOf(val.getRoom_id()));
        roomType.setText(String.valueOf(""));
        arrivalDate.setText(String.valueOf(val.getArrivalDate()));
        checkOutDate.setText(String.valueOf(val.getCheckOutDate()));
    }

    private void closeWindow(){
        Stage primaryStage = (Stage) closeButton.getScene().getWindow();
        primaryStage.hide();
    }

}
