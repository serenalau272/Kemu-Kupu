package com.se206.g11.components;

import com.se206.g11.MainApp;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class Clock {

    private Arc sector;
    private float angle;
    private Color startColor = Color.rgb(172, 249, 75);
    private Color midColor1 = Color.rgb(238, 255, 132);
    private Color midColor2 = Color.rgb(255, 198, 132);
    private Color endColor = Color.rgb(255, 111, 116);
    private Color color = startColor;
    private Label timerLabel;
    private int duration = MainApp.getSetting().getTimerDuration();
    private MyTimer timer;
    
    public Clock(Arc sector, Label timerLabel) {
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
        timer = new MyTimer(this.duration);
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
        double ratio;
        if (angle <= 120.0f){
            //between start and mid1
            ratio = angle / 120.0f;
            color = startColor.interpolate(midColor1, ratio);

        } else if (angle <= 240.0f){
            //between mid1 and mid2
            ratio = (angle - 120.0f) / 120.0f;
            color = midColor1.interpolate(midColor2, ratio);

        } else {
            //between mid2 and end
            ratio = (angle - 240.0f) / 120.0f;
            color = midColor2.interpolate(endColor, ratio);
        }
        
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