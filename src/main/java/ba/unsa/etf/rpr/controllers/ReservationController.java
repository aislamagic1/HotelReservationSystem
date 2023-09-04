package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.ReservationsManager;
import ba.unsa.etf.rpr.business.RoomsManager;
import ba.unsa.etf.rpr.dao.ReservationsDao;
import ba.unsa.etf.rpr.dao.ReservationsDaoSQLImpl;
import ba.unsa.etf.rpr.dao.RoomsDao;
import ba.unsa.etf.rpr.dao.RoomsDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Reservations;
import ba.unsa.etf.rpr.domain.RoomTypes;
import ba.unsa.etf.rpr.domain.Rooms;
import ba.unsa.etf.rpr.exception.ReservationsException;
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
import java.util.*;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ReservationController {

    public Button cancelButton;
    public Label priceLabel;
    public DatePicker arrivalDatePicker;
    public DatePicker checkOutDatePicker;
    private RoomTypes roomType;
    private List<Pair<LocalDate, LocalDate>> dates;

    private int guestId;
    private double price;

    //manager
    private final ReservationsManager reservationsManager = new ReservationsManager();
    private final RoomsManager roomsManager = new RoomsManager();

    public void setRoomType(RoomTypes roomType){
        this.roomType = roomType;
        dates = reservationsManager.getAllSchedulesForRooms(roomType.getId());
    }
    public void setRoomPrice(double price){
        this.price = price;
    }

    public void setGuestId(int id){
        this.guestId = id;
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
                        if(arrivalDatePicker.getValue() != null && checkOutDatePicker.getValue() != null){
                            long daysBetween = ChronoUnit.DAYS.between(arrivalDatePicker.getValue(), checkOutDatePicker.getValue());
                            priceLabel.setText(String.valueOf(price * daysBetween));
                        }
                    }
                };
            }
        });
    }

    /**
     * Method that finds all the dates that are repeated x amount of times
     * @param datePairs list of dates
     * @param repetitionCount x repeated times
     * @return list of dates
     */
    private List<LocalDate> findRepeatedDates(List<Pair<LocalDate, LocalDate>> datePairs, int repetitionCount){
        Map<LocalDate, Integer> dateCountMap = new HashMap<>();

        for (Pair<LocalDate, LocalDate> datePair : datePairs) {
            LocalDate startDate = datePair.getKey();
            LocalDate endDate = datePair.getValue();

            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                dateCountMap.put(currentDate, dateCountMap.getOrDefault(currentDate, 0) + 1);
                currentDate = currentDate.plusDays(1);
            }
        }

        List<LocalDate> repeatedDates = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : dateCountMap.entrySet()) {
            if (entry.getValue() == repetitionCount) {
                repeatedDates.add(entry.getKey());
            }
        }

        return repeatedDates;
    }

    private void closeWindow(){
        Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
        primaryStage.hide();
    }

    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        closeWindow();
    }

    public void makeReservationButton(ActionEvent actionEvent) throws ReservationsException {
        if(arrivalDatePicker.getValue() == null){
            arrivalDatePicker.getStyleClass().add("wrongField");
            System.out.println("Empty arrival date!");
        }else if(checkOutDatePicker.getValue() == null){
            checkOutDatePicker.getStyleClass().add("wrongField");
            System.out.println("Empty check-out date!");
        }else{
            Reservations reservation = new Reservations();
            reservation.setArrivalDate(java.sql.Date.valueOf(arrivalDatePicker.getValue()));
            reservation.setCheckOutDate(java.sql.Date.valueOf(checkOutDatePicker.getValue()));

            List<Rooms> rooms = roomsManager.getRoomsWithSameRoomType(roomType.getId());

            reservation.setRoom_id(rooms.get(0).getId());
            reservation.setGuest(guestId);

            reservationsManager.add(reservation);
            closeWindow();
        }

    }
}
