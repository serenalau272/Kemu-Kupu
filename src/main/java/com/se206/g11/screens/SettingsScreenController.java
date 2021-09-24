package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SettingsScreenController extends ApplicationController implements Initializable {
    @FXML ImageView music_toggle;
    @FXML ImageView exit_button;
    @FXML Label speedVal;
    @FXML ImageView minus;
    @FXML ImageView plus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    
}
