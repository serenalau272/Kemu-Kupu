package com.controllers.modals;

import com.MainApp;
import com.components.animations.SpinningWheel;
import com.controllers.ModalController;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the attributions modal.
 */
public class Wheel extends ModalController {
    @FXML
    private ImageView wheel;
    Animation anim;
    private final int[] rewardStars = {5, 3, 10, 3, 50, 0};

    private void giveReward() {
        double rotation = wheel.getRotate();
        int truncateFactor = ((int) rotation) / 360;
        int rotatedBy = (int) rotation - truncateFactor * 360;
        int segment = (int) Math.floor(((rotatedBy + 30.0) % 360) / 60.0); // should be 0 to 5

        System.out.println(rewardStars[segment]);
    }

    @Override
    protected void start() {
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
