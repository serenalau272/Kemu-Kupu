package com.components.animations;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class WheelTimer {

    private Label timerLabel;
    private int timeSeconds;
    private MyTimer timer;
    private final int duration = 120;
    
    public WheelTimer(Label timerLabel) {
        this.timerLabel = timerLabel;
        timeSeconds = 0;
        timer = new MyTimer(duration);
    }

    public void stop(){
        timer.close();
    }

    public void start(){
        timeSeconds = duration;
        timer.start();
    }

    private void updateLabel(){
        timerLabel.setText(String.valueOf(timeSeconds));
    }

    public class MyTimer extends AnimationTimer {
        private long lastUpdate = 0;
        private long durationTick;

        public MyTimer(long durationSec){
            this.durationTick = durationSec * 1000_000_000;
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
            if (timeSeconds <= 0) {
                stop();
            }

            timeSeconds--;
            updateLabel();
        }
    }
}