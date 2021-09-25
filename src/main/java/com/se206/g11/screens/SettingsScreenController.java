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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This class is the controller for the settings modal.
 */
public class SettingsScreenController extends ApplicationController implements Initializable {
    //The users current settings Selection
    private Settings settings;

    @FXML Label speedVal;
    @FXML ImageView music_toggle_button;
    @FXML ImageView exit_button;
    @FXML ImageView minus_button;
    @FXML ImageView plus_button;

    //// Private Methods ////

    /**
     * Update display elements with the current settings configuration.
     */
    private void __update() {
        this.speedVal.setText(String.format("%.1f", this.settings.getSpeechSpeed()));
        try {
            this.setImage((this.settings.getMusic()) ? "on" : "off", this.music_toggle_button);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Close this modal
     */
    private void __close() {
        MainApp.closeModal();
    }

    /**
     * Change the speech speed of festival.
     * Note that we don't have ot do a check here if the speed is out of bounds (< 0), as this is
     * checked inside of the settinsg class itself.
     * @param amt the amount to change by
     */
    private void __speed_change(double amt) {
        this.settings.setSpeechSpeed(this.settings.getSpeechSpeed()+amt);
        this.__update();
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        this.settings = MainApp.getSettings();
        this.__update();
        
        //Set event handlers
        this.exit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__close());  
        this.music_toggle_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings.setMusic(!this.settings.getMusic());
            this.__update();
        });
        this.minus_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__speed_change(-0.1));
        this.plus_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__speed_change(0.1));
    }
}
