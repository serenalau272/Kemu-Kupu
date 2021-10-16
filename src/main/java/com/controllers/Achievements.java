package com.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.ApplicationController;
import com.MainApp;
import com.enums.Achievement;
import com.models.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * This class is the controller for the achievements modal.
 */
public class Achievements extends ApplicationController implements Initializable {
    @FXML ImageView exitButton;
    User user;

    /**
     * Close this modal
     */
    private void __close() {
        MainApp.closeModal();
    }
    
    //// Public Methods ////

    private void setAchievementsVisibility(){
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
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        this.user = MainApp.getUser();

        //Set event handlers
        this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__close());  
    }
}
