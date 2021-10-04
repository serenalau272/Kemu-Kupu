package com.se206.g11.models;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;

public class ClockWork {
    private Arc sector;
    private float angle = 0.0f;
    private Color color = Color.GREEN;

    //https://docs.oracle.com/javafx/2/api/javafx/animation/FillTransition.html
    
    public ClockWork(Arc sector) {
        this.sector = sector;
        AnimationTimer timer = new MyTimer();
        timer.start();
    }

    // private void updateColor(){
    //     if (angle > 270.0f){

    //     } else if (angle > 180.0f){

    //     } else if (angle > 90.0f){

    //     } else {

    //     }
    // }

    private class MyTimer extends AnimationTimer {

        @Override
        public void handle(long now) {
            doHandle();
        }

        private void doHandle() {
            angle += 1.0f;
            sector.setLength(angle);
            sector.setFill(color);

            if (angle >= 360.0f) {
                stop();
                System.out.println("Animation stopped");
            }
        }
    }
}