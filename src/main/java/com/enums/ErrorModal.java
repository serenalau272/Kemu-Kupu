package com.enums;

import java.io.IOException;

import com.MainApp;
import com.models.GeneralModal;
import com.util.Sounds;

/**
 * An enum to represent the status of a word being tested.
 */
public enum ErrorModal implements GeneralModal {
    USERNAME,
    NICKNAME;

    public String getMessage() {
        switch (this) {
            case USERNAME:
                return "An account already exists with this username. Please pick another username.";
            case NICKNAME:
                return "Please ensure your nickname is between 1 to 10 characters inclusive.";
            default:
                System.err.println("ERROR: Error type not implemented.");
                return "";
        }
    }

    public void doAction() {
        
    }
}