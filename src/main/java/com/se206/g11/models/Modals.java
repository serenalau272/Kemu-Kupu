package com.se206.g11.models;

public enum Modals {
    REWARD,
    SETTING,
    ATTRIBUTION;

    public String getFileName() {
        switch (this) {
            case REWARD: {
                return "Reward";
            }
            case SETTING: {
                return "Setting";
            }
            case ATTRIBUTION: {
                return "Attribution";
            }
            default: {
                System.err.println("ERROR (Modals.java): Type not implemented for getting file name!");
                return "Menu";
            }
        }
    }

    public String getWindowName() {
        switch (this) {
            case REWARD: {
                return "Reward";
            }
            case SETTING: {
                return "Settings";
            }
            case ATTRIBUTION: {
                return "Kemu Kupu - Asset Attributions";
            }
            default: {
                System.err.println("ERROR (modals.java): Type not implemented for getting window name!");
                return "Kemu Kupu";
            }
        }
    }
}
