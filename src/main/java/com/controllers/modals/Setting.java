package com.controllers.modals;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import com.App;
import com.controllers.ApplicationController;
import com.util.Sounds;

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
    private com.models.Setting settings;

    @FXML Label speedVal;
    @FXML Label timeVal;
    @FXML ImageView musicToggleButton;
    @FXML ImageView soundToggleButton;
    @FXML ImageView exitButton;
    @FXML ImageView speedMinusButton;
    @FXML ImageView speedPlusButton;
    @FXML ImageView timeMinusButton;
    @FXML ImageView timePlusButton;
    @FXML ImageView resetButton;

    //// Private Methods ////

    /**
     * Update display elements with the current settings configuration.
     */
    private void __update() {
        this.speedVal.setText(String.format("%.2f", this.settings.getSpeechSpeed()));
        this.timeVal.setText(this.settings.getTimerDuration() + "s");
        try {
            this.setImage((this.settings.getMusic()) ? "on" : "off", this.musicToggleButton);
            this.setImage((this.settings.getSounds()) ? "on" : "off", this.soundToggleButton);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load image for music toggling");
            e.printStackTrace();
        }
    }

    /**
     * Close this modal
     */
    private void __close() {
        App.setSetting(this.settings);
        App.closeModal();
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
        this.settings = App.getSetting();
        this.__update();
        
        //Set event handlers
        this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__close());  
        this.musicToggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings.setMusic(!this.settings.getMusic());
            App.updateMusic();
            Sounds.playSoundEffect("pop");
            this.__update();
        });
        this.soundToggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings.setSounds(!this.settings.getSounds());
            Sounds.playSoundEffect("pop");
            this.__update();
        });
        this.speedMinusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__speed_change(-0.25));
        this.speedPlusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__speed_change(0.25));
        this.timeMinusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__time_change(-5));
        this.timePlusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__time_change(5));
        this.resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings = new com.models.Setting();
            App.setSetting(this.settings);
            Sounds.playSoundEffect("pop");
            App.updateMusic();
            this.__update();
        });
    }
}
