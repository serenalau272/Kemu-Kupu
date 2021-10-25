package com.controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.animations.SpinningWheel;
import com.components.animations.WheelTimer;
import com.controllers.ApplicationController;
import com.enums.Achievement;
import com.enums.ErrorModal;
import com.enums.Views;
import com.models.User;
import com.util.Modal;
import com.util.Sounds;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * This class is the controller for the attributions modal.
 */
public class Wheel extends ApplicationController implements Initializable {
    @FXML
    private ImageView wheel;
    @FXML
    private Label timerMessage;
    @FXML
    private Label starMessage;
    @FXML
    private ImageView backButton;
    @FXML
    private ImageView spinButton;
    @FXML
    private ImageView popup;
    @FXML
    private ImageView collectButton;
    @FXML
    private Label starNum;

    private User currentUser;
    private WheelTimer timer;
    private Animation anim;
    private int reward;
    private final int[] rewardStars = { 3, 2, 1, 10, 1, 2, 5, 1 };

    private void addPocketAchievement(int bound, int stars) {
        if (stars >= bound) {
            String s;
            try {
                s = Achievement.fromString("star" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                currentUser.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    private void giveReward() {
        double rotation = wheel.getRotate();
        int truncateFactor = ((int) rotation) / 360;
        int rotatedBy = (int) rotation - truncateFactor * 360;
        int segment = (int) Math.floor(((rotatedBy + 270) % 360) / 45.0); // should be 0 to 7

        reward = rewardStars[segment];

        PauseTransition wait = new PauseTransition(new Duration(800));
        wait.setOnFinished(e -> {
            setPopupVisibility(true);
            starNum.setText(Integer.toString(reward) + " stars");
            Sounds.playSoundEffect("reward");
        });

        wait.play();
    }

    private void collectReward() {
        try {
            currentUser.addScore(-1, reward);
            int totalStars = currentUser.getTotalStars();
            addPocketAchievement(10, totalStars);
            addPocketAchievement(50, totalStars);
            addPocketAchievement(100, totalStars);
            addPocketAchievement(200, totalStars);
            addPocketAchievement(300, totalStars);
        } catch (IOException exception) {
            System.err.println("Unable to add stars " + reward);
            Modal.showGeneralModal(ErrorModal.INTERNET);
        }

        MainApp.getGlobalTimer().restart();
        timer.start();
        spinButton.setVisible(false);
        updatestarMessage();

        setPopupVisibility(false);
    }

    private void updatestarMessage() {
        starMessage.setText(Integer.toString(currentUser.getTotalStars()));
    }

    private void spin() {
        if (MainApp.getGlobalTimer().getDuration() <= 0) {
            anim.play();
        }
    }

    private void setPopupVisibility(boolean visibility) {
        popup.setVisible(visibility);
        collectButton.setVisible(visibility);
        starNum.setVisible(visibility);
    }

    @Override
    protected void start() {
        setPopupVisibility(false);

        timer = new WheelTimer(timerMessage, spinButton);
        timer.start();
        updatestarMessage();

        anim = new SpinningWheel(wheel).getAnimator();
        anim.setOnFinished(e -> giveReward());

        spinButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> spin());
        collectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> collectReward());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();

        currentUser = MainApp.getUser();

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MainApp.setRoot(Views.PROFILE);
            timer.stop();
        });

    }

}
