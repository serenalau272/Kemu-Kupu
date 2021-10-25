package com.models;

/**
 * Settings for the GUI application which can be altered in Game Mode. Allows
 * settings for speed speed, enabling of music, enabling of sound effects and
 * setting timer duration
 */
public class Setting {
    private boolean hasMusic = true;
    private boolean hasSound = true;
    private Double speechSpeed = 1.0;
    private int timerDuration = 30;

    /**
     * @return whether music is enabled
     */
    public boolean getMusic() {
        return this.hasMusic;
    }

    /**
     * Set whether music is available or not
     * 
     * @param val
     */
    public void setMusic(boolean val) {
        this.hasMusic = val;
    }

    /**
     * @return whether sound is enabled
     */
    public boolean getSounds() {
        return this.hasSound;
    }

    /**
     * Set whether sound is available or not
     * 
     * @param val
     */
    public void setSounds(boolean val) {
        this.hasSound = val;
    }

    /**
     * @return the speed speed
     */
    public Double getSpeechSpeed() {
        return this.speechSpeed;
    }

    /**
     * @return the duration factor for festival
     */
    public Double getDurationFactor() {
        return 1.0 / this.speechSpeed;
    }

    /**
     * Set the speaking speed of the tts implementation (festival)
     * 
     * @param s
     * @return whether speed successfully set
     */
    public Boolean setSpeechSpeed(Double s) {
        if (s < 0.5 || s > 2)
            return false;

        this.speechSpeed = s;
        return true;
    }

    /**
     * get the duration of the quiz timer
     * 
     * @return
     */
    public int getTimerDuration() {
        return this.timerDuration;
    }

    /**
     * set the duration of the quiz timer
     * 
     * @param s
     * @return
     */
    public Boolean setTimerDuration(int val) {
        if (val < 15 || val > 60)
            return false;

        this.timerDuration = val;
        return true;
    }
}
