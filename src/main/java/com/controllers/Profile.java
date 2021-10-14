package com.controllers;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ApplicationController;
import com.MainApp;
import com.models.Game;
import com.util.Sounds;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Profile extends ApplicationController implements Initializable {
    private int highScore;

    @FXML ImageView resetButton;
    @FXML ImageView exitButton;
    @FXML ImageView highScoreLabel;

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
            setImage(highScore, highScoreLabel);
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
        this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__close());  

        this.resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            File userStats = new File("./.user/.userStats.txt");
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
