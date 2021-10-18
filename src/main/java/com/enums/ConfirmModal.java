package com.enums;

import com.MainApp;
import com.util.Modal;
import com.util.Sounds;

/**
 * An enum to represent the status of a word being tested.
 */
public enum ConfirmModal {
    MENU,
    REPLAY,
    SIGNOUT,
    RESET;

    public String getMessage() {
        switch (this) {
            case MENU:
            case REPLAY:
                return "Are you sure you want to exit the game? Current results will be lost.";
            case SIGNOUT:
                return "Are you sure you would like to sign out?";
            case RESET:
                return "Are you sure you would like to reset all user statistics? You can't undo this action.";
            default:
                System.err.println("ERROR: Confirmation type not implemented.");
                return "";
        }
    }

    public void doConfirmedAction() {
        switch (this) {
            case MENU:
                MainApp.setRoot(View.MENU);
                break;
            case REPLAY:
                MainApp.setRoot(View.GAMEMODE);
                Sounds.playMusic("menu");
                break;
            case SIGNOUT:
                MainApp.setUser();
                MainApp.setRoot(View.MENU);
                //@TODO signout user 
                break;
            case RESET:
                MainApp.setRoot(View.PROFILE);
                //@TODO reset stats 
                break;
            default:
                System.err.println("ERROR: Confirmation type not implemented.");
                return;
        }
    }
}