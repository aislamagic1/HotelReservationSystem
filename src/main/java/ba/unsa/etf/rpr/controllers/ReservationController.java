package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.RoomTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ReservationController {

    public Button cancelButton;
    public Label priceLabel;
    public DatePicker arrivalDatePicker;
    private RoomTypes roomType;

    public void setRoomType(RoomTypes roomType){
        this.roomType = roomType;
    }
    public void setRoomPrice(String price){
        priceLabel.setText(price);
    }

    @FXML
    void initialize(){

    }

    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
        primaryStage.hide();
    }
}
