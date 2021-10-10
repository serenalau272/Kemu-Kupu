package com.se206.g11.controllers;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.models.Game;
import com.se206.g11.util.Sounds;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Profile extends ApplicationController implements Initializable {
    private int highScore;

    @FXML ImageView reset_button;
    @FXML ImageView exit_button;
    @FXML ImageView highScore_label;

    //// Private Methods ////

    /**
     * Close this modal
     */
    private void __close() {
        MainApp.closeModal();
    }

    private void updateHighScore() {
        try {
            highScore = Game.getHighScore();
            setImage(highScore, highScore_label);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        updateHighScore();
        
        //Set event handlers
        this.exit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__close());  

        this.reset_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            File userStats = new File("./.user/.userStats.txt");
            System.out.println("hi");
            try {
                BufferedWriter statsWriter = new BufferedWriter(new FileWriter(userStats, false));
                statsWriter.close();
                updateHighScore();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
