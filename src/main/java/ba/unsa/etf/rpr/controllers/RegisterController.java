package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RegisterController {
    public Button returnButton;
    public TextField firstName;
    public TextField lastName;
    public TextField eMail;

    public TextField password;

    private void checkEmptyTextField(TextField x){
        if(x.getText().trim().isEmpty()){
            x.getStyleClass().removeAll("correctField");
            x.getStyleClass().add("wrongField");
        }else{
            x.getStyleClass().removeAll("wrongField");
            x.getStyleClass().add("correctField");
        }
    }

    private void isEmptyField(TextField x){
        x.getStyleClass().add("wrongField");
    }

    @FXML
    void initialize(){
        firstName.textProperty().addListener((observableValue, odlVal, newVal) ->{
            checkEmptyTextField(firstName);
        });

        lastName.textProperty().addListener((observableValue, odlVal, newVal) ->{
            checkEmptyTextField(lastName);
        });

        eMail.textProperty().addListener((observableValue, odlVal, newVal) ->{
            checkEmptyTextField(eMail);
        });

        password.textProperty().addListener((observableValue, odlVal, newVal) ->{
            checkEmptyTextField(password);
        });
    }

    public void returnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        stage.setTitle("Hotel Reservation System");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        Stage primaryStage = (Stage) returnButton.getScene().getWindow();
        primaryStage.hide();
        System.out.println("Returning to login screen");
    }

    public void createNewAccountBtnClick(ActionEvent actionEvent) {
        if(firstName.getText().isEmpty()){
            isEmptyField(firstName);
            System.out.println("Enter first name!");
        }else if(lastName.getText().isEmpty()){
            isEmptyField(lastName);
            System.out.println("Enter last name!");
        }else if(eMail.getText().isEmpty()){
            isEmptyField(eMail);
            System.out.println("Enter email!");
        }else if(password.getText().isEmpty()){
            isEmptyField(password);
            System.out.println("Enter password!");
        }
    }
}
