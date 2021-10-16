package com.controllers.modals;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.controllers.ModalController;
import com.models.Game;
import com.util.Sounds;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Profile extends ModalController {
    @FXML ImageView resetButton;
    @FXML ImageView highScoreLabel;

    //// Private Methods ////

    private void updateHighScore() {
        try {
            int highScore = Game.getHighScore();
            setImage(highScore, highScoreLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //// Public Methods ////

    @Override
    public void initializeModal() {
        super.initializeModal();
        updateHighScore();
        
        //Set event handlers

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
