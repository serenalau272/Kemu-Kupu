package com.components.animations;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SlideComponentHorizontal {
    private ImageView image;
    private Animation anim;
    private double initialX;
    private double delta;
    private Duration duration;

    public SlideComponentHorizontal(ImageView image, Duration duration, double delta){
        this.image = image;
        this.initialX = image.getTranslateX();        
        this.delta = delta;
        this.duration = duration;
    }

    private void constructAnimator(){
        anim = new Transition() {
            {
                setCycleDuration(duration);
                setCycleCount(1);
            }
        
            protected void interpolate(double frac) {
                image.setTranslateX(initialX + frac * delta);
            }
        };
    }

    public Animation getAnimator(){
        constructAnimator();
        return anim;
    }

}