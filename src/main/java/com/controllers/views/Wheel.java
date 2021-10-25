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
    //// Properties ////
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

    //// Private (helper) Methods ////

    /**
     * Attempts to unlock a star achievement for the current user
     * @param bound the level to compare against
     * @param stars the stars the user has earned
     */
    private void __addPocketAchievement(int bound, int stars) {
        if (stars >= bound) {
            String s;
            try {
                s = Achievement.fromString("star" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                this.currentUser.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    /**
     * Provide a reward to the user in the form of a popup to be collected.
     */
    private void __giveReward() {
        double rotation = wheel.getRotate();
        int truncateFactor = ((int) rotation) / 360;
        int rotatedBy = (int) rotation - truncateFactor * 360;
        int segment = (int) Math.floor(((rotatedBy + 270) % 360) / 45.0); // should be 0 to 7

        this.reward = rewardStars[segment];

        PauseTransition wait = new PauseTransition(new Duration(800));
        wait.setOnFinished(e -> {
            this.__setPopupVisibility(true);
            this.starNum.setText(Integer.toString(this.reward) + " stars");
            Sounds.playSoundEffect("reward");
        });

        wait.play();
    }

    /**
     * The user collects their rewards.
     * This function adds the given stars to their total.
     */
    private void __collectReward() {
        try {
            this.currentUser.addScore(-1, this.reward);
            int totalStars = this.currentUser.getTotalStars();
            this.__addPocketAchievement(10, totalStars);
            this.__addPocketAchievement(50, totalStars);
            this.__addPocketAchievement(100, totalStars);
            this.__addPocketAchievement(200, totalStars);
            this.__addPocketAchievement(300, totalStars);
        } catch (IOException exception) {
            System.err.println("Unable to add stars " + this.reward);
            Modal.showGeneralModal(ErrorModal.INTERNET);
        }

        MainApp.getGlobalTimer().restart();
        this.timer.start();
        this.spinButton.setVisible(false);
        this.__updatestarMessage();

        this.__setPopupVisibility(false);
    }

    /**
     * Update the star label with the number of stars they have been awarded.
     */
    private void __updatestarMessage() {
        this.starMessage.setText(Integer.toString(this.currentUser.getTotalStars()));
    }

    /**
     * The user has clicked the spin wheel, so this resets the global timer and then 
     * begins the spinning animation.
     */
    private void __spin() {
        if (MainApp.getGlobalTimer().getDuration() <= 0) {
            this.anim.play();
        }
    }

    /**
     * Toggle the visibility of the reward popup
     * @param visibility a boolean, if true shows the popup, hides it otherwise
     */
    private void __setPopupVisibility(boolean visibility) {
        this.popup.setVisible(visibility);
        this.collectButton.setVisible(visibility);
        this.starNum.setVisible(visibility);
    }

    //// Public Methods ////

    @Override
    protected void start() {
        this.__setPopupVisibility(false);

        this.timer = new WheelTimer(this.timerMessage, this.spinButton);
        this.timer.start();
        this.__updatestarMessage();

        this.anim = new SpinningWheel(this.wheel).getAnimator();
        this.anim.setOnFinished(e -> this.__giveReward());

        this.spinButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.__spin());
        this.collectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.__collectReward());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();

        this.currentUser = MainApp.getUser();

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MainApp.setRoot(Views.PROFILE);
            this.timer.stop();
        });

    }

}
