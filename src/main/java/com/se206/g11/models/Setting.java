package com.se206.g11.models;

/**
 * Settings for the GUI application which can be altered in Game Mode. Allows settings for speed speed and enabling of music
 */
public class Setting {
    private boolean music = true;
    private Double speechSpeed = 1.0;

    /**
     * Serialize the settings from a file
     * Note: unused in A3 implementation but may be useful for Project
     * @param p the path to the file
     */
    public void load(String p) {
        //TODO
    }

    /**
     * Save the settings to the path
     * Note: unused in A3 implementation but may be useful for Project
     * @param p the path to save to
     */
    public void save(String p) {
        //TODO
    }

    /**
     * @return whether music is enabled
     */
    public boolean getMusic() {
        return this.music;
    }

    /**
     * @return the speed speed
     */
    public Double getSpeechSpeed() {
        return this.speechSpeed;
    }

    /**
     * @return the duration speed factor for Festival
     */
    public Double getSpeedFactor(){
        return 1.0 / this.speechSpeed;
    }

    /**
     * Set whether music is available or not
     * @param e
     */
    public void setMusic(boolean e) {
        this.music = e;
    }
    
    /**
     * Set the speaking speed of the tts implementation (festival)
     * @param s
     * @return whether speed successfully set
     */
    public Boolean setSpeechSpeed(Double s) {
        if (s < 0.5 || s > 2) return false;

        this.speechSpeed = s;
        return true;
    }
}
