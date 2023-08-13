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
import java.util.Date;
import java.util.List;

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
        arrivalDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item, empty);
                        ReservationsDao reservationsDao = new ReservationsDaoSQLImpl();
                        List<Pair<LocalDate, LocalDate>> dates = reservationsDao.getAllSchedulesForRooms(roomType.getId());
                        if(item.isBefore(LocalDate.now())){
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }else{
                            for(Pair<LocalDate, LocalDate> date : dates){
                                LocalDate arrival = date.getKey();
                                LocalDate checkOut = date.getValue();
                                if(item.equals(arrival) || item.equals(checkOut) || item.isAfter(arrival) || item.isBefore(checkOut)){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
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
}
