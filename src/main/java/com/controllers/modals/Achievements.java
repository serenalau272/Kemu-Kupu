package com.controllers.modals;

import com.MainApp;
import com.controllers.ModalController;
import com.enums.Achievement;
import com.models.User;

import javafx.scene.layout.Pane;

/**
 * This class is the controller for the achievements modal.
 */
public class Achievements extends ModalController {
    User user;
    
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
    public void initializeModal() {
        super.initializeModal();
        this.user = MainApp.getUser();
    }
}
