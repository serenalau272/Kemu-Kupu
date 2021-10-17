package com.controllers.views;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.controllers.ApplicationController;
import com.controllers.ModalController;
import com.enums.View;
import com.models.Game;
import com.util.Sounds;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Profile extends ApplicationController implements Initializable {
    @FXML ImageView backButton;
    @FXML ImageView editUsername;
    @FXML ImageView editNickname;
    @FXML ImageView passwordButton;
    @FXML ImageView resetButton;
    @FXML ImageView signoutButton;
    @FXML ImageView shopButton;
    @FXML ImageView achievementsButton;
    @FXML Label nameLabel;


    //// Private Methods ////

    private void updateHighScore() {
        try {
            int highScore = Game.getHighScore();
            //@TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        updateHighScore();
        
        //Set event handlers

        this.resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            //@TODO
        });

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.MENU));
        this.shopButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.SHOP));
        this.achievementsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.ACHIEVEMENT));
    }
}
