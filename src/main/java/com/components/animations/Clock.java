package com.components.animations;

import com.MainApp;
import com.enums.Gamemode;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

/**
 * The Clock component is the timer component used in the Quiz during Game Mode.
 * It features a segment rotating arc with colour changing interpolation as
 * well.
 */
public class Clock {
    // javaFX fields to alter
    private Arc sector;
    private Label timerLabel;

    // clock properties
    private float angle;
    private int duration;
    private MyTimer timer;

    // fields for colour interpolation
    private Color startColor = Color.rgb(172, 249, 75);
    private Color midColor1 = Color.rgb(238, 255, 132);
    private Color midColor2 = Color.rgb(255, 198, 132);
    private Color endColor = Color.rgb(255, 111, 116);
    private Color color = startColor;

    /**
     * Construct clock
     * 
     * @param sector
     * @param timerLabel
     */
    public Clock(Arc sector, Label timerLabel) {
        this.sector = sector;
        this.timerLabel = timerLabel;
        configureArc();
        timer = new MyTimer(duration);
    }

    /**
     * Stop clock if quiz is in GameMode
     */
    public void stop() {
        if (MainApp.getGameState().getGameMode() == Gamemode.PRACTICE)
            return;

        timer.close();
    }

    /**
     * Start clock if quiz is in GameMode
     */
    public void start() {
        if (MainApp.getGameState().getGameMode() == Gamemode.PRACTICE)
            return;

        angle = 0.0f;
        resume();
    }

    /**
     * Resume clock if quiz is in GameMode and is awaiting input
     */
    public void resume() {
        if (MainApp.getGameState().getGameMode() == Gamemode.PRACTICE)
            return;

        if (!MainApp.getGameState().getAwaitingInput())
            return;

        duration = MainApp.getSetting().getTimerDuration();
        timer = new MyTimer(this.duration);
        timer.start();
    }

    /**
     * get score multiplier - 4 is returned regardless of time when in practice
     * mode.
     * 
     * for game mode: - 4 is returned if 0 <= angle <= 90 (within first quarter of
     * the time) - 3 is returned if 90 < angle <= 180 (within second quarter of the
     * time) - 2 is returned if 180 < angle <= 270 (within third quarter of the
     * time) - 1 is returned if 270 < angle <= 360 (within fourth quarter of the
     * time)
     * 
     * @return the score multiplier based upon time. 4 is maximum and 1 is minimum
     */
    public int getScoreMultiplier() {
        if (MainApp.getGameState().getGameMode() == Gamemode.PRACTICE)
            return 4;

        int multiplier = (int) Math.ceil((360 - angle) * 4 / 360); // get the multiplier by taking the angle of the arc

        return (multiplier == 0) ? 1 : multiplier; // cater for edge case when multipler == 0
    }

    /**
     * update the colour of the arc
     */
    private void updateColor() {
        double ratio;

        if (angle <= 120.0f) {
            // between start and mid1
            ratio = angle / 120.0f;
            color = startColor.interpolate(midColor1, ratio);

        } else if (angle <= 240.0f) {
            // between mid1 and mid2
            ratio = (angle - 120.0f) / 120.0f;
            color = midColor1.interpolate(midColor2, ratio);

        } else {
            // between mid2 and end
            ratio = (angle - 240.0f) / 120.0f;
            color = midColor2.interpolate(endColor, ratio);
        }

        sector.setFill(color);
    }

    /**
     * Update the label based on the current angle
     */
    private void updateLabel() {
        int display = duration - (int) (angle * duration / 360);
        timerLabel.setText(String.valueOf(display));
    }

    /**
     * Configure the arc on load
     */
    private void configureArc() {
        sector.setStartAngle(90.0f);
        sector.setType(ArcType.ROUND);
        sector.setStrokeWidth(0.0f);
    }

    /**
     * Timer class for Quiz Clock
     */
    public class MyTimer extends AnimationTimer {
        private long lastUpdate = 0;
        private long durationTick;

        /**
         * configure durationTick to have the correct delay (we update once per half
         * degree rotation)
         * 
         * @param durationSec
         */
        public MyTimer(long durationSec) {
            this.durationTick = durationSec * 1000_000_000 / 720;
        }

        /**
         * close timer
         */
        public void close() {
            stop();
        }

        /**
         * call doHandle() for every half degree rotation
         */
        @Override
        public void handle(long now) {
            if (now - lastUpdate >= durationTick) {
                lastUpdate = now;
                doHandle();
            }
        }

        /**
         * action method on every half rotation Increment angle by half a degree and
         * update the sector accordingly In addition, update colour and label.
         */
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