package com.components.animations;

import javafx.animation.AnimationTimer;

/**
 * GlobalTimer is used to keep track of how long a user has to wait globally
 * before they have to spin the wheel
 */
public class GlobalTimer {
    private int timeSeconds;
    private MyTimer timer;
    private int duration;

    /**
     * Construct GlobalTimer
     * 
     * @param duration
     */
    public GlobalTimer(int duration) {
        this.duration = duration;
        timeSeconds = 0;
        timer = new MyTimer();
        timer.start();
    }

    /**
     * Stop timer
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Restart timer
     */
    public void restart() {
        timeSeconds = duration;
        timer.start();
    }

    /**
     * Get current duration of timer
     * 
     * @return
     */
    public int getDuration() {
        return timeSeconds;
    }

    /**
     * Timer class for global timer
     */
    public class MyTimer extends AnimationTimer {
        private long lastUpdate = 0;
        private long durationTick = 1_000_000_000; // every second

        /**
         * Call doHandle() every second
         */
        @Override
        public void handle(long now) {
            if (now - lastUpdate >= durationTick) {
                lastUpdate = now;
                doHandle();
            }
        }

        /**
         * action function to decrement time in seconds by 1 and stop if reaching 0
         */
        private void doHandle() {
            if (timeSeconds <= 1) {
                stop();
            }

            timeSeconds--;
        }
    }
}
