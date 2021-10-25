package com.components.animations;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * SlideComponentHorizontal is used to animate the secondary buttons from the
 * menu offscreen before transition
 */
public class SlideComponentHorizontal {
    private ImageView image;
    private Animation anim;
    private double initialX;
    private double delta;
    private Duration duration;

    /**
     * Construct SlideComponentHorizontal
     * 
     * @param image
     * @param duration
     * @param delta    // delta is the total offset of the image's horizontal
     *                 position
     */
    public SlideComponentHorizontal(ImageView image, Duration duration, double delta) {
        this.image = image;
        this.initialX = image.getTranslateX();
        this.delta = delta;
        this.duration = duration;
    }

    /**
     * Construct the animator for a single cycle of given duration
     */
    private void constructAnimator() {
        anim = new Transition() {
            {
                setCycleDuration(duration);
                setCycleCount(1);
            }

            // move the image along the horizontal axis by a given fraction of delta
            protected void interpolate(double frac) {
                image.setTranslateX(initialX + frac * delta);
            }
        };
    }

    /**
     * construct the animator and return this
     * 
     * @return
     */
    public Animation getAnimator() {
        constructAnimator();
        return anim;
    }

}