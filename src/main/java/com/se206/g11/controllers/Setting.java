package com.se206.g11.controllers;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.util.Sounds;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Setting extends ApplicationController implements Initializable {
    //The users current settings Selection
    private com.se206.g11.models.Setting settings;

    @FXML Label speedVal;
    @FXML Label timeVal;
    @FXML ImageView music_toggle_button;
    @FXML ImageView sound_toggle_button;
    @FXML ImageView exit_button;
    @FXML ImageView speed_minus_button;
    @FXML ImageView speed_plus_button;
    @FXML ImageView time_minus_button;
    @FXML ImageView time_plus_button;
    @FXML ImageView reset_button;

    //// Private Methods ////

    /**
     * Update display elements with the current settings configuration.
     */
    private void __update() {
        this.speedVal.setText(String.format("%.2f", this.settings.getSpeechSpeed()));
        this.timeVal.setText(this.settings.getTimerDuration() + "s");
        try {
            this.setImage((this.settings.getMusic()) ? "on" : "off", this.music_toggle_button);
            this.setImage((this.settings.getSounds()) ? "on" : "off", this.sound_toggle_button);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load image for music toggling");
            e.printStackTrace();
        }
    }

    /**
     * Close this modal
     */
    private void __close() {
        MainApp.setSetting(this.settings);
        MainApp.closeModal();
    }

    /**
     * Change the speech speed of festival.
     * Note that we don't have ot do a check here if the speed is out of bounds (< 0), as this is
     * checked inside of the settinsg class itself.
     * @param amt the amount to change by
     */
    private void __speed_change(double amt) {
        Sounds.playSoundEffect("pop");
        this.settings.setSpeechSpeed(this.settings.getSpeechSpeed()+amt);
        this.__update();
    }

    private void __time_change(int amt) {
        Sounds.playSoundEffect("pop");
        this.settings.setTimerDuration(this.settings.getTimerDuration()+amt);
        this.__update();
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        this.settings = MainApp.getSetting();
        this.__update();
        
        //Set event handlers
        this.exit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__close());  
        this.music_toggle_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings.setMusic(!this.settings.getMusic());
            MainApp.updateMusic();
            Sounds.playSoundEffect("pop");
            this.__update();
        });
        this.sound_toggle_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings.setSounds(!this.settings.getSounds());
            Sounds.playSoundEffect("pop");
            this.__update();
        });
        this.speed_minus_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__speed_change(-0.25));
        this.speed_plus_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__speed_change(0.25));
        this.time_minus_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__time_change(-5));
        this.time_plus_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__time_change(5));
        this.reset_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings = new com.se206.g11.models.Setting();
            MainApp.setSetting(this.settings);
            Sounds.playSoundEffect("pop");
            MainApp.updateMusic();
            this.__update();
        });
    }
}
