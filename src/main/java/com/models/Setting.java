package com.models;

/**
 * Settings for the GUI application which can be altered in Game Mode. Allows
 * settings for speed speed and enabling of music
 */
public class Setting {
    private boolean music = true;
    private boolean sounds = true;
    private Double speechSpeed = 1.0;
    private int timerDuration = 30;

    /**
     * Serialize the settings from a file Note: unused in A3 implementation but may
     * be useful for Project
     * 
     * @param p the path to the file
     */
    public void load(String p) {
        // TODO
    }

    /**
     * Save the settings to the path Note: unused in A3 implementation but may be
     * useful for Project
     * 
     * @param p the path to save to
     */
    public void save(String p) {
        // TODO
    }

    /**
     * @return whether music is enabled
     */
    public boolean getMusic() {
        return this.music;
    }

    /**
     * Set whether music is available or not
     * 
     * @param e
     */
    public void setMusic(boolean e) {
        this.music = e;
    }

    public boolean getSounds() {
        return this.sounds;
    }

    public void setSounds(boolean e) {
        this.sounds = e;
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

    public int getTimerDuration() {
        return this.timerDuration;
    }

    public Boolean setTimerDuration(int s) {
        if (s < 15 || s > 60)
            return false;

        this.timerDuration = s;
        return true;
    }
}
