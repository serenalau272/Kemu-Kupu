package com.components;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BouncingImageView {
    @FXML private ImageView image;
    private Animation anim;
    private double initialY;
    private final int amplitude = 10;

    public BouncingImageView(ImageView image){
        this.image = image;
        this.initialY = image.getTranslateY();

        anim = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
                setCycleCount(Animation.INDEFINITE);
            }
        
            protected void interpolate(double frac) {
                image.setTranslateY(initialY + (Math.sin(frac * Math.PI * 2) * amplitude));
            }
        
        };
    }

    public Animation getAnimator(){
        return anim;
    }

}
