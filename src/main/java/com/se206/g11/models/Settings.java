package com.se206.g11.models;

public class Settings {
    private boolean music = true;
    private Double speechSpeed = 1.0;

    /**
     * Initalize settings with default values
     */
    public Settings() {
        
    }

    /**
     * Serialize the settings from a file
     * @param p the path to the file
     */
    public void load(String p) {
        //TODO
    }

    /**
     * Save the settings to the path
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
     */
    public Boolean setSpeechSpeed(Double s) {
        if (s < 0.5 || s > 2) return false;
        this.speechSpeed = s;
        return true;
    }
}
