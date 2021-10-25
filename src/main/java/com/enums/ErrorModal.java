package com.enums;

import com.models.GeneralModal;

/**
 * An enum to represent the status of a word being tested.
 */
public enum ErrorModal implements GeneralModal {
    USERNAME, NICKNAME, INTERNET;
    
    /**
     * Get the string that should be displayed to the user on this modal.
     * In the event that a string does not exist for this modal, returns an empty string.
     */
    public String getMessage() {
        switch (this) {
        case USERNAME:
            return "An account already exists with this username. Please pick another username.";
        case NICKNAME:
            return "Please ensure your nickname is between 1 to 10 characters inclusive.";
        case INTERNET:
            return "Unable to connect to the internet. Please connect to an internet connection to play this game!";
        default:
            System.err.println("ERROR: Error type not implemented.");
            return "";
        }
    }

    public void doAction() {

    }
}