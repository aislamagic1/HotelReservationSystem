package ba.unsa.etf.rpr.controllers;

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

    private void closeWindow(){
        Stage primaryStage = (Stage) closeButton.getScene().getWindow();
        primaryStage.hide();
    }

}
