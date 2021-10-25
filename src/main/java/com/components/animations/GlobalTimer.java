package com.components.animations;

import javafx.animation.AnimationTimer;

public class GlobalTimer {
    private int timeSeconds;
    private MyTimer timer;
    private int duration;

    public GlobalTimer(int duration) {
        this.duration = duration;
        timeSeconds = 0;
        timer = new MyTimer();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void restart() {
        timeSeconds = duration;
        timer.start();
    }

    public int getDuration() {
        return timeSeconds;
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
        }
    }
}
