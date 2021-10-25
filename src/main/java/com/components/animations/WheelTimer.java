package com.components.animations;

import com.MainApp;
import com.controllers.ApplicationController;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * WheelTimer allows for the label on ProfileScreen and SpinningWheel to reflect the value of the global timer
 */
public class WheelTimer extends ApplicationController {
    private Label timerLabel;
    private ImageView spinButton = null;
    private int timeSeconds;
    private MyTimer timer;

    /**
     * Create wheel timer
     * @param timerLabel
     * @param spinButton
     */
    public WheelTimer(Label timerLabel, ImageView spinButton) {
        this.spinButton = spinButton;
        this.timerLabel = timerLabel;
        timeSeconds = MainApp.getGlobalTimer().getDuration();
        timer = new MyTimer();
    }

    public void start() {
        timeSeconds = MainApp.getGlobalTimer().getDuration();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    private void updateLabel() {
        if (timeSeconds <= 0) {
            if (spinButton == null) {
                timerLabel.setText("Spin!");
            } else {
                timerLabel.setText("");
                spinButton.setVisible(true);
            }
            return;
        }

        int numMinutes = timeSeconds / 60;
        int numSeconds = timeSeconds - (numMinutes * 60);
        timerLabel.setText(String.valueOf(numMinutes) + "m " + String.valueOf(numSeconds) + "s");
    }

    public class MyTimer extends AnimationTimer {
        private long lastUpdate = 0;
        private long durationTick = 1_000_000_000;

        @Override
        public void handle(long now) {
            if (now - lastUpdate >= durationTick) {
                lastUpdate = now;
                doHandle();
            }
        }

        private void doHandle() {
            if (timeSeconds <= 1) {
                stop();
            }

            timeSeconds--;
            updateLabel();
        }
    }
}