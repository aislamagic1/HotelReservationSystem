package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.RoomTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainMenuController {
    public Button logoutButton;
    public Label userName;

    @FXML
    private TableView<RoomTypes> tableView;
    @FXML
    private TableColumn<RoomTypes, String> roomType;
    @FXML
    private TableColumn<RoomTypes, Integer> numOfPersons;
    @FXML
    private TableColumn<RoomTypes, Double> price;

    @FXML
    public void initialize(){
        roomType.setCellValueFactory(new PropertyValueFactory<RoomTypes, String>("roomType"));
        numOfPersons.setCellValueFactory(new PropertyValueFactory<RoomTypes, Integer>("numPersons"));
        price.setCellValueFactory(new PropertyValueFactory<RoomTypes, Double>("price"));
    }

    public void displayUserName(String name){
        userName.setText(name);
    }

    private void returnToLogoutScreen() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        stage.setTitle("Hotel Reservation System");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        Stage primaryStage = (Stage) logoutButton.getScene().getWindow();
        primaryStage.hide();
        System.out.println("Returning to login screen.");
    }

    public void logoutButtonClick(ActionEvent actionEvent) throws IOException {
        returnToLogoutScreen();
    }

    public void logoutMenuClick(ActionEvent actionEvent) throws IOException {
        returnToLogoutScreen();
    }
}
