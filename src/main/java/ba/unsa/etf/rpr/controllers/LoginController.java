package ba.unsa.etf.rpr.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    public TextField fieldUsername;
    public PasswordField passwordUsername;

    private void wrongUsername(){
        fieldUsername.getStyleClass().removeAll("correctField");
        fieldUsername.getStyleClass().add("wrongField");
    }
    private void correctUsername(){
        fieldUsername.getStyleClass().removeAll("wrongField");
        fieldUsername.getStyleClass().add("correctField");
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
    }

}
