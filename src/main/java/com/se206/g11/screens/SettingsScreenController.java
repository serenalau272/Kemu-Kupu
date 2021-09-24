package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.models.Settings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SettingsScreenController extends ApplicationController implements Initializable {
    //The users current settings Selection
    private Settings settings;

    @FXML Label speedVal;
    @FXML ImageView music_toggle_button;
    @FXML ImageView exit_button;
    @FXML ImageView minus_button;
    @FXML ImageView plus_button;

    /**
     * Update display elements with the current settings configuration.
     */
    private void __updateDisplay() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        this.settings = MainApp.getSettings();
        this.__updateDisplay();
    }    
}
