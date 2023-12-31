package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.ReservationsManager;
import ba.unsa.etf.rpr.business.RoomTypesManager;
import ba.unsa.etf.rpr.dao.ReservationsDao;
import ba.unsa.etf.rpr.dao.ReservationsDaoSQLImpl;
import ba.unsa.etf.rpr.dao.RoomTypesDao;
import ba.unsa.etf.rpr.dao.RoomTypesDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Reservations;
import ba.unsa.etf.rpr.domain.RoomTypes;
import ba.unsa.etf.rpr.exception.ReservationsException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainMenuController {
    public Button logoutButton;
    public Label userName;
    public ListView<String> listView;
    public Button viewDetails;
    private int guestId;

    private List<Reservations> reservations;

    @FXML
    private TableView<RoomTypes> tableView;
    @FXML
    private TableColumn<RoomTypes, String> roomType;
    @FXML
    private TableColumn<RoomTypes, Integer> numOfPersons;
    @FXML
    private TableColumn<RoomTypes, Double> roomPrice;

    //manager
    private final ReservationsManager reservationsManager = new ReservationsManager();
    private final RoomTypesManager roomTypesManager = new RoomTypesManager();

    @FXML
    public void initialize(){
        roomType.setCellValueFactory(new PropertyValueFactory<RoomTypes, String>("roomType"));
        numOfPersons.setCellValueFactory(new PropertyValueFactory<RoomTypes, Integer>("numPersons"));
        roomPrice.setCellValueFactory(new PropertyValueFactory<RoomTypes, Double>("roomPrice"));

        List<RoomTypes> list = roomTypesManager.getAll();
        ObservableList<RoomTypes> roomTypesList = FXCollections.observableArrayList(list);

        tableView.setItems(roomTypesList);
    }

    public void setListView(int guestId){
        reservations =  reservationsManager.getAllReservationsForGuest(guestId);
        for(Reservations reservation : reservations) {
            listView.getItems().add(String.valueOf(reservation.getArrivalDate()));
        }
    }

    public void updateListView(){
        List<Reservations> newReservations =  reservationsManager.getAllReservationsForGuest(guestId);
        if(newReservations.size() > reservations.size()){
            int i = 0;
            for(Reservations x : newReservations){
                if(i > reservations.size() - 1)
                    listView.getItems().add(String.valueOf(x.getArrivalDate()));
                i = i + 1;
            }
        }else if(newReservations.size() < reservations.size()){
            int lastIndex = listView.getItems().size() - 1;
            listView.getItems().remove(lastIndex);
        }
        reservations = newReservations;
    }

    public void setGuestId(int id){
        this.guestId = id;
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

    private void openNewWindow(String title, String file, Boolean openMakeReservation, int selectedId) throws IOException, ReservationsException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        Parent root = loader.load();

        if(openMakeReservation) {
            ReservationController reservationController = loader.getController();
            reservationController.setRoomType(tableView.getItems().get(selectedId));
            reservationController.setRoomPrice(tableView.getItems().get(selectedId).getRoomPrice());
            reservationController.setGuestId(guestId);
        }else{
            ReservationInfoController reservationInfoController = loader.getController();
            reservationInfoController.setReservationAndLabels(reservations.get(selectedId));
        }

        stage.setTitle(title);
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void logoutButtonClick(ActionEvent actionEvent) throws IOException {
        returnToLogoutScreen();
    }

    public void logoutMenuClick(ActionEvent actionEvent) throws IOException {
        returnToLogoutScreen();
    }

    public void makeReservationBtnClick(ActionEvent actionEvent) throws IOException, ReservationsException {
        updateListView();
        int selectedId = tableView.getSelectionModel().getSelectedIndex();
        if(selectedId != -1) {
            openNewWindow("Reservation", "/fxml/reservation.fxml", true, selectedId);
        }
    }

    public void viewDetailsBtnClick(ActionEvent actionEvent) throws IOException, ReservationsException {
        updateListView();
        int selectedId = listView.getSelectionModel().getSelectedIndex();
        if(selectedId != -1 && selectedId <= listView.getItems().size()) {
            openNewWindow("Reservation Info", "/fxml/reservationInfo.fxml", false, selectedId);
        }
    }
}
