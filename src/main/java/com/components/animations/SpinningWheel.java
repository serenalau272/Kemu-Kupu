package com.components.animations;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * SpinningWheel was used to conduct the actual spinning of the wheel animation
 */
public class SpinningWheel {
    private Animation anim;
    private final int amplitude = 100;

    /**
     * Construct SpinningWheel
     * 
     * @param image
     */
    public SpinningWheel(ImageView image) {

        // create animation transition with a single 2s transition
        anim = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
                setCycleCount(1);
            }

            // increment the wheels current rotation by a random amount. We used a half
            // sinusoid to have smaller increments at the start and end and larger
            // increments in the middle
            protected void interpolate(double frac) {
                double currentRotation = image.getRotate();
                image.setRotate(currentRotation + (Math.sin(frac * Math.PI) * amplitude * new Random().nextInt(3)));
            }

        };
    }

    /**
     * get the animator
     * 
     * @return
     */
    public Animation getAnimator() {
        return anim;
    }

}
