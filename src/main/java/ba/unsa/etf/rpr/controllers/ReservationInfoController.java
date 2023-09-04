package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.ReservationsManager;
import ba.unsa.etf.rpr.dao.ReservationsDao;
import ba.unsa.etf.rpr.dao.ReservationsDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Reservations;
import ba.unsa.etf.rpr.exception.ReservationsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ReservationInfoController {
    public Label roomNumber;
    public Label arrivalDate;
    public Label checkOutDate;
    public Button closeButton;

    private Reservations reservation;

    //manager
    private final ReservationsManager reservationsManager = new ReservationsManager();

    public void setReservationAndLabels(Reservations val){
        reservation = val;
        roomNumber.setText(String.valueOf(reservation.getRoom_id()));
        arrivalDate.setText(String.valueOf(reservation.getArrivalDate()));
        checkOutDate.setText(String.valueOf(reservation.getCheckOutDate()));
    }

    private void closeWindow(){
        Stage primaryStage = (Stage) closeButton.getScene().getWindow();
        primaryStage.hide();
    }

    public void closeBtnClick(ActionEvent actionEvent) {
        closeWindow();
    }

    public void cancelReservationBtnClick(ActionEvent actionEvent) throws ReservationsException {
        reservationsManager.delete(reservation.getId());
        closeWindow();
    }
}
