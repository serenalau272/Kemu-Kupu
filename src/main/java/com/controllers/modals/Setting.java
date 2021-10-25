package com.controllers.modals;

import java.io.FileNotFoundException;

import com.MainApp;
import com.controllers.ModalController;
import com.util.Modal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Setting extends ModalController {
    // The users current settings Selection
    private com.models.Setting settings;

    @FXML
    Label speedVal;
    @FXML
    Label timeVal;
    @FXML
    ImageView musicToggleButton;
    @FXML
    ImageView soundToggleButton;
    @FXML
    ImageView speedMinusButton;
    @FXML
    ImageView speedPlusButton;
    @FXML
    ImageView timeMinusButton;
    @FXML
    ImageView timePlusButton;
    @FXML
    ImageView resetButton;
    @FXML
    ImageView exitButton;

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
     * Change the speech speed of festival. Note that we don't have ot do a check
     * here if the speed is out of bounds (< 0), as this is checked inside of the
     * settinsg class itself.
     * 
     * @param amt the amount to change by
     */
    private void __speed_change(double amt) {
        this.settings.setSpeechSpeed(this.settings.getSpeechSpeed() + amt);
        this.__update();
    }

    /**
     * Change the timer duration
     * 
     * @param amt the amount to change by
     */
    private void __time_change(int amt) {
        this.settings.setTimerDuration(this.settings.getTimerDuration() + amt);
        this.__update();
    }

    //// Public Methods ////

    @Override
    public void initializeModal() {
        this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> Modal.closeModal(true));
        this.settings = MainApp.getSetting();
        this.__update();

        this.musicToggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings.setMusic(!this.settings.getMusic());
            MainApp.updateMusic();
            this.__update();
        });

        this.soundToggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings.setSounds(!this.settings.getSounds());
            this.__update();
        });

        this.speedMinusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__speed_change(-0.25));
        this.speedPlusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__speed_change(0.25));

        this.timeMinusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__time_change(-5));
        this.timePlusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__time_change(5));

        //reset settings back to default
        this.resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.settings = new com.models.Setting();
            MainApp.setSetting(this.settings);
            MainApp.updateMusic();
            this.__update();
        });
    }
}
