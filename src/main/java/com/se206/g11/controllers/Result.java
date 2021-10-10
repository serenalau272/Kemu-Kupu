package com.se206.g11.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.enums.View;
import com.se206.g11.util.Sounds;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Result extends ApplicationController implements Initializable{
    @FXML TextField hiddenField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();

        Sounds.playMusic("menu");
        
        hiddenField.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                MainApp.setRoot(View.REWARD);
            }
        });
    }
    
    
}
