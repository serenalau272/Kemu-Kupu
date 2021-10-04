package com.se206.g11.models;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ClockWork {
    private Arc sector;
    private float angle = 0.0f;
    private Color startColor = Color.GREEN;
    private Color endColor = Color.RED;
    private Color color = startColor;

    //https://docs.oracle.com/javafx/2/api/javafx/animation/FillTransition.html
    
    public ClockWork(Arc sector) {
        this.sector = sector;
        configureArc();
        AnimationTimer timer = new MyTimer(30);
        timer.start();
    }

    private void updateColor(){
        double ratio = angle / 360.0f;
        color = startColor.interpolate(endColor, ratio);
    }
    
    private void configureArc(){
        sector.setStartAngle(90.0f);
        sector.setType(ArcType.ROUND);
        sector.setStrokeWidth(0.0f);
    }

    private class MyTimer extends AnimationTimer {
        private long lastUpdate = 0;
        private long durationTick;

        public MyTimer(long durationSec){
            this.durationTick = durationSec * 1000_000_000 / 720;
        }

        @Override
        public void handle(long now) {
            // System.out.println(now);
            if (now - lastUpdate >= durationTick) {
                lastUpdate = now ;
                doHandle();
            }
        }

        private void doHandle() {
            angle += 0.5f;
            sector.setLength(angle);
            updateColor();
            sector.setFill(color);

            if (angle >= 360.0f) {
                stop();
                System.out.println("Animation stopped");
            }
        }
    }
}