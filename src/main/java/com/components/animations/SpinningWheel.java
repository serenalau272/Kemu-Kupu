package com.components.animations;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpinningWheel {
    @FXML
    private ImageView image;
    private Animation anim;
    private final int amplitude = 100;

    public SpinningWheel(ImageView image) {
        this.image = image;

        anim = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
                setCycleCount(1);
            }

            protected void interpolate(double frac) {
                double currentRotation = image.getRotate();
                image.setRotate(currentRotation + (Math.sin(frac * Math.PI) * amplitude * new Random().nextInt(3)));
            }

        };
    }

    public Animation getAnimator() {
        return anim;
    }

}
