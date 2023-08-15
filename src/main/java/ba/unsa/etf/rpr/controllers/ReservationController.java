package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.ReservationsDao;
import ba.unsa.etf.rpr.dao.ReservationsDaoSQLImpl;
import ba.unsa.etf.rpr.domain.RoomTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ReservationController {

    public Button cancelButton;
    public Label priceLabel;
    public DatePicker arrivalDatePicker;
    public DatePicker checkOutDatePicker;
    private RoomTypes roomType;
    private List<Pair<LocalDate, LocalDate>> dates;

    public void setRoomType(RoomTypes roomType){
        this.roomType = roomType;
        ReservationsDao reservationsDao = new ReservationsDaoSQLImpl();
        dates = reservationsDao.getAllSchedulesForRooms(roomType.getId());
    }
    public void setRoomPrice(String price){
        priceLabel.setText(price);
    }

    private LocalDate getClosestArrivalDate(LocalDate arrivalDate){
        LocalDate closest = null;
        long closestDifference = Long.MAX_VALUE;
        for(Pair<LocalDate, LocalDate> date : dates){
            if(date.getKey().isAfter(arrivalDate)){
                long difference = ChronoUnit.DAYS.between(arrivalDate, date.getKey());
                if(difference < closestDifference){
                    closestDifference = difference;
                    closest = date.getKey();
                }
            }
        }
        return closest;
    }

    @FXML
    void initialize(){
        checkOutDatePicker.setDisable(true);
        arrivalDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item, empty);
                        if(item.isBefore(LocalDate.now())){
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }else{
                            for(Pair<LocalDate, LocalDate> date : dates){
                                LocalDate arrival = date.getKey();
                                LocalDate checkOut = date.getValue();
                                if(item.equals(arrival) || item.equals(checkOut) || (item.isAfter(arrival) && item.isBefore(checkOut))){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        }
                    }
                };
            }
        });

        arrivalDatePicker.valueProperty().addListener((observable, oldValue, newValue) ->{
            checkOutDatePicker.setValue(null);

            checkOutDatePicker.setDisable(newValue == null);

            if(arrivalDatePicker.getValue() != null){
                arrivalDatePicker.getStyleClass().removeAll("wrongField");
            }
        });

        checkOutDatePicker.valueProperty().addListener((observable, oldValue, newValue) ->{
            if(checkOutDatePicker.getValue() != null){
                checkOutDatePicker.getStyleClass().removeAll("wrongField");
            }
        });

        checkOutDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (arrivalDatePicker.getValue() != null) {
                            LocalDate closestDate = getClosestArrivalDate(arrivalDatePicker.getValue());
                            if (item.equals(arrivalDatePicker.getValue()) || item.isBefore(arrivalDatePicker.getValue()) || (closestDate != null && item.isAfter(closestDate))) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    }
                };
            }
        });
    }

    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
        primaryStage.hide();
    }

    public void makeReservationButton(ActionEvent actionEvent) {
        if(arrivalDatePicker.getValue() == null){
            arrivalDatePicker.getStyleClass().add("wrongField");
            System.out.println("Empty arrival date!");
        }else if(checkOutDatePicker.getValue() == null){
            checkOutDatePicker.getStyleClass().add("wrongField");
            System.out.println("Empty check-out date!");
        }else{
            Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
            primaryStage.hide();
        }

    }
}
