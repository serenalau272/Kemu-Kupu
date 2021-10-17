package com.controllers.views;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.controllers.ApplicationController;
import com.enums.View;
import com.models.Game;
import com.models.User;
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
    User currentUser;

    @FXML ImageView backButton;
    @FXML ImageView editUsername;
    @FXML ImageView editNickname;
    @FXML ImageView passwordButton;
    @FXML ImageView resetButton;
    @FXML ImageView signoutButton;
    @FXML ImageView shopButton;
    @FXML ImageView achievementsButton;
    @FXML Label nameLabel;
    @FXML Label starLabel;
    @FXML Label achievementsLabel;
    @FXML Label scoreLabel;


    //// Private Methods ////

    private void updateHighScore() {
        // try {
        //     // int highScore = Game.getHighScore();     //TODO: are we getting from both???
        //     //@TODO
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
    //// Public Methods ////

    private void configureStaticEntries(){
        nameLabel.setText("Hello " + currentUser.getNickname()+"!");
        starLabel.setText(Integer.toString(currentUser.getNumStars()));
        achievementsLabel.setText(Integer.toString(currentUser.getNumAchievements()) + "/18");
    }

    private void configureDynamicEntries(){
        scoreLabel.setText(Integer.toString(currentUser.getHighScore()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        updateHighScore();
        currentUser = MainApp.getUser();
        
        configureStaticEntries();
        configureDynamicEntries();
        //Set event handlers

        this.signoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO: yet to be fully linked
            Sounds.playSoundEffect("pop");
            MainApp.setUser();
            MainApp.setRoot(View.MENU);
        });
        this.resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            currentUser.setHighScore(0);
            configureDynamicEntries();
        });

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.MENU));
        this.shopButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.SHOP));
        this.achievementsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.ACHIEVEMENT));
    }
}
