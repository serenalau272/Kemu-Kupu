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
    private float angle;
    private Color startColor = Color.GREEN;
    private Color endColor = Color.RED;
    private Color color = startColor;
    private Label timerLabel;
    private int duration;
    private MyTimer timer;
    
    public ClockWork(Arc sector, Label timerLabel) {
        this.sector = sector;
        this.timerLabel = timerLabel;
        configureArc();
        timer = new MyTimer(duration);
    }

    public void stop(){
        timer.close();
    }

    public void start(){
        angle = 0.0f;
        duration = 15;
        timer = new MyTimer(duration);
        timer.start();
    }

    /**
     * 
     * @return the score multiplier based upon time. 4 is maximum and 1 is minimum
     */
    public int getScoreMultiplier(){
        int multiplier = (int) Math.ceil((360 - angle) * 4 / 360);
        return (multiplier == 0) ? 1 : multiplier; 
    }

    private void updateColor(){
        double ratio = angle / 360.0f;
        color = startColor.interpolate(endColor, ratio);
        sector.setFill(color);
    }

    private void updateLabel(){
        int display = duration - (int) (angle * duration / 360);
        timerLabel.setText(String.valueOf(display));
    }
    
    private void configureArc(){
        sector.setStartAngle(90.0f);
        sector.setType(ArcType.ROUND);
        sector.setStrokeWidth(0.0f);
    }

    public class MyTimer extends AnimationTimer {
        private long lastUpdate = 0;
        private long durationTick;

        public MyTimer(long durationSec){
            this.durationTick = durationSec * 1000_000_000 / 720;
        }

        public void close(){
            stop();
        }

        @Override
        public void handle(long now) {
            if (now - lastUpdate >= durationTick) {
                lastUpdate = now ;
                doHandle();
            }
        }

        private void doHandle() {
            angle += 0.5f;
            sector.setLength(angle);
            updateColor();
            updateLabel();
            

            if (angle >= 360.0f) {
                stop();
            }
        }
    }
}