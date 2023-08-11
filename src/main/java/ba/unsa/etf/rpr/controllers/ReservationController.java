package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.RoomTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ReservationController {

    public Button cancelButton;
    private RoomTypes roomType;

    public void setRoomType(RoomTypes roomType){
        this.roomType = roomType;
    }


    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-menu.fxml"));
        Parent root = loader.load();
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
        primaryStage.hide();
    }
}
