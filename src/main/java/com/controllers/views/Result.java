package com.controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.ResultsList;
import com.controllers.ApplicationController;
import com.enums.Views;
import com.util.Sounds;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Result extends ApplicationController implements Initializable {
    @FXML
    TextField hiddenField;

    @Override
    protected void start() {
        ResultsList.configureEntries();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();

        Sounds.playMusic("menu");

        hiddenField.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                MainApp.setRoot(Views.REWARD);
            }
        });
    }

}
