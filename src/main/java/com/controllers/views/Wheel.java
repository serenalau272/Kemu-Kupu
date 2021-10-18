package com.controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.animations.SpinningWheel;
import com.components.animations.WheelTimer;
import com.controllers.ApplicationController;
import com.controllers.ModalController;
import com.enums.View;
import com.models.User;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the attributions modal.
 */
public class Wheel extends ApplicationController implements Initializable {
    @FXML
    private ImageView wheel;
    @FXML
    private Label timerLabel;
    @FXML
    private Label starLabel;
    @FXML
    private ImageView exitButton;

    User currentUser;
    WheelTimer timer;
    Animation anim;
    private final int[] rewardStars = {3, 2, 1, 10, 1, 2, 5, 1};

    private void giveReward() {
        double rotation = wheel.getRotate();
        int truncateFactor = ((int) rotation) / 360;
        int rotatedBy = (int) rotation - truncateFactor * 360;
        int segment = (int) Math.floor(((rotatedBy + 270) % 360) / 45.0); // should be 0 to 7

        int stars = rewardStars[segment];
        System.out.println(stars);
        currentUser.changeStarCount(stars);

        MainApp.getGlobalTimer().restart();
        timer.start();
        updateStarLabel();
    }

    private void updateStarLabel(){
        starLabel.setText(Integer.toString(currentUser.getNumStars()));
    }

    @Override
    protected void start() {
        timer = new WheelTimer(timerLabel);
        timer.start();
        updateStarLabel();

        anim = new SpinningWheel(wheel).getAnimator();
        anim.setOnFinished(e -> giveReward());

        wheel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (MainApp.getGlobalTimer().getDuration() <= 0) {
                // spin!
                anim.play();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();

        currentUser = MainApp.getUser();

        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MainApp.setRoot(View.MENU);
            timer.stop();
        });
        
    }

}
