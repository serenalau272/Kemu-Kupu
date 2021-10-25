package com.components.animations;

import com.MainApp;
import com.controllers.ApplicationController;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * WheelTimer allows for the label on ProfileScreen and SpinningWheel to reflect
 * the value of the global timer
 */
public class WheelTimer extends ApplicationController {
    private Label timerMessage;
    private ImageView spinButton = null;
    private int timeSeconds;
    private MyTimer timer;

    /**
     * Create wheel timer
     * 
     * @param timerMessage
     * @param spinButton
     */
    public WheelTimer(Label timerMessage, ImageView spinButton) {
        this.spinButton = spinButton;
        this.timerMessage = timerMessage;
        timeSeconds = MainApp.getGlobalTimer().getDuration(); // retrieve duration from global timer
        timer = new MyTimer();
    }

    /**
     * start timer
     */
    public void start() {
        timeSeconds = MainApp.getGlobalTimer().getDuration(); // retrieve duration from global timer
        timer.start();
    }

    /**
     * stop timer
     */
    public void stop() {
        timer.stop();
    }

    /**
     * update label based upon global timer value
     */
    private void updateLabel() {
        // if we can spin, update accorindly
        if (timeSeconds <= 0) {
            if (spinButton == null) { // as is on profile screen, update text only
                timerMessage.setText("Spin!");
            } else { // as is on spin the wheel screen, make button visible
                timerMessage.setText("");
                spinButton.setVisible(true);
            }
            return;
        }

        // if time has not reached the end, get the appropriate label, and update
        int numMinutes = timeSeconds / 60;
        int numSeconds = timeSeconds - (numMinutes * 60);
        timerMessage.setText(String.valueOf(numMinutes) + "m " + String.valueOf(numSeconds) + "s");
    }

    /**
     * Timer class for WheelTimer
     */
    public class MyTimer extends AnimationTimer {
        private long lastUpdate = 0;
        private long durationTick = 1_000_000_000; // every second

        /**
         * call doHandle() every second to update the GUI accordingly
         */
        @Override
        public void handle(long now) {
            if (now - lastUpdate >= durationTick) {
                lastUpdate = now;
                doHandle();
            }
        }

        /**
         * decreement time every second and update label OR stop if limit reached
         */
        private void doHandle() {
            if (timeSeconds <= 1) {
                stop();
            }

            timeSeconds--;
            updateLabel();
        }
    }
}