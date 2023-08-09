package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.GuestsDao;
import ba.unsa.etf.rpr.dao.GuestsDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Guests;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {
    public TextField fieldUsername;
    public PasswordField fieldPassword;
    public Button loginButton;

    private void wrongUsername(){
        fieldUsername.getStyleClass().removeAll("correctField");
        fieldUsername.getStyleClass().add("wrongField");
    }
    private void correctUsername(){
        fieldUsername.getStyleClass().removeAll("wrongField");
        fieldUsername.getStyleClass().add("correctField");
    }

    private void wrongPassword(){
        fieldPassword.getStyleClass().removeAll("correctField");
        fieldPassword.getStyleClass().add("wrongField");
    }
    private void correctPassword(){
        fieldPassword.getStyleClass().removeAll("wrongField");
        fieldPassword.getStyleClass().add("correctField");
    }

    @FXML
    public void initialize(){
        fieldUsername.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String o, String n){
                    if(fieldUsername.getText().trim().isEmpty()){
                        wrongUsername();
                    }else{
                        correctUsername();
                    }
            }
        }
        );

        fieldPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
                public void changed(ObservableValue<? extends String> observableValue, String o, String n){
                    if(fieldPassword.getText().trim().isEmpty()){
                        wrongPassword();
                    }else{
                        correctPassword();
                    }
            }
        }
        );
    }

    public void loginClick(ActionEvent actionEvent) throws IOException {
        if(fieldUsername.getText().isEmpty()){
            fieldUsername.getStyleClass().add("wrongField");
            System.out.println("Empty email");
        }else if(fieldPassword.getText().isEmpty()){
            fieldPassword.getStyleClass().add("wrongField");
            System.out.println("Empty password");
        }else{
            GuestsDao guestsDao = new GuestsDaoSQLImpl();
            Guests guest = guestsDao.getGuestByEmailAndPassword(fieldUsername.getText(), fieldPassword.getText());
            if(guest.getEmail() == null || guest.getPassword() == null){
                System.out.println("Incorrect email or password. Please try again.");
                wrongPassword();
                wrongUsername();
            }else{
                correctUsername();
                correctPassword();
                System.out.println("Login successful");
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-menu.fxml"));
                Parent root = loader.load();
                stage.setTitle("Main Menu");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
                Stage primaryStage = (Stage) loginButton.getScene().getWindow();
                primaryStage.hide();
            }
        }
    }

    public void registerClick(ActionEvent actionEvent) {

    }
}


