package com.controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.controllers.ApplicationController;
import com.enums.Achievement;
import com.enums.View;
import com.models.User;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * This class is the controller for the achievements modal.
 */
public class Achievements extends ApplicationController implements Initializable {
    @FXML ImageView backButton;
    
    User user;
    
    //// Public Methods ////

    private void setAchievementsVisibility() {
        for (Achievement achievement : user.getProcuredAchievements()){
            int achievementId = achievement.getId();
            findNodesByID((Pane) MainApp.getStackPane(), Integer.toString(achievementId)).setVisible(true);;
        }
    }

    @Override
    protected void start() {
        setAchievementsVisibility();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();
        this.user = MainApp.getUser();

        // add button handlers

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.PROFILE));


    }
}
