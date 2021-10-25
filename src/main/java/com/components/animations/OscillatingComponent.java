package com.components.animations;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * OscillatingComponent is used to oscillate both the play button and avatar
 * along the vertical axis
 */
public class OscillatingComponent {
    private Animation anim;
    private double initialY;
    private final int amplitude = 10;

    public OscillatingComponent(ImageView image) {
        this.initialY = image.getTranslateY();

        // create transition animation with 2s indefinite cycles
        anim = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
                setCycleCount(Animation.INDEFINITE);
            }

            // using a sinusoid function we alter the y position of the ImageView
            protected void interpolate(double frac) {
                image.setTranslateY(initialY + (Math.sin(frac * Math.PI * 2) * amplitude));
            }

        };
    }

    /**
     * return the animator
     * 
     * @return
     */
    public Animation getAnimator() {
        return anim;
    }

}
