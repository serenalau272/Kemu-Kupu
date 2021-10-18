package com.controllers.views;

import com.MainApp;
import com.components.animations.SpinningWheel;
import com.components.animations.WheelTimer;
import com.controllers.ModalController;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the attributions modal.
 */
public class Wheel extends ModalController {
    @FXML
    private ImageView wheel;
    @FXML
    private Label timerLabel;
    WheelTimer timer;
    Animation anim;
    private final int[] rewardStars = {1, 10, 1, 2, 3, 1, 5, 2};

    private void giveReward() {
        double rotation = wheel.getRotate();
        int truncateFactor = ((int) rotation) / 360;
        int rotatedBy = (int) rotation - truncateFactor * 360;
        int segment = (int) Math.floor(((rotatedBy + 1) % 360) / 45.0); // should be 0 to 7

        System.out.println(rewardStars[segment]);
    }

    @Override
    protected void start() {
        if (MainApp.getWheelTimer() != null){
            timer = MainApp.getWheelTimer();
        } else {
            timer = new WheelTimer(timerLabel);
        }

        anim = new SpinningWheel(wheel).getAnimator();
        anim.setOnFinished(e -> giveReward());

        wheel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (MainApp.canSpin()) {
                // spin!
                anim.play();
            } else {
                // not enough time waiting
                System.out.println("Wait some more kiddo");
            }
        });
    }

}
