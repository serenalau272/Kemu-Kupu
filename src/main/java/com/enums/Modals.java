package com.enums;

/**
 * Represents modals that may be shown to the user, a centralised location for file locations and window names.
 */
public enum Modals {
    SETTING, ATTRIBUTION, PAUSE, HELP, CONFIRMATION, ERROR;

    /**
     * Get the name of the fxml file for this modal.
     * @return a string representing a filename, should have .fxml appended.
     */
    public String getFileName() {
        switch (this) {
            case SETTING: {
                return "Setting";
            }
            case ATTRIBUTION: {
                return "Attribution";
            }
            case PAUSE: {
                return "Pause";
            }
            case HELP: {
                return "Help";
            }
            case CONFIRMATION: {
                return "Confirmation";
            }
            case ERROR: {
                return "Error";
            }
            default: {
                System.err.println("ERROR (Modals.java): Type not implemented for getting file name!");
                return "Menu";
            }
        }
    }

    /**
     * The name of the window for a given modal.
     * @return a string representing the name of the modal window to be spawned.
     */
    public String getWindowName() {
        switch (this) {
            case SETTING: {
                return "Settings";
            }
            case ATTRIBUTION: {
                return "Kemu Kupu - Asset Attributions";
            }
            case PAUSE: {
                return "Game Paused";
            }
            case HELP: {
                return "Help";
            }
            case CONFIRMATION: {
                return "Confirm";
            }
            case ERROR: {
                return "Error";
            }
            default: {
                System.err.println("ERROR (modals.java): Type not implemented for getting window name!");
                return "Kemu Kupu";
            }
        }
    }
}
