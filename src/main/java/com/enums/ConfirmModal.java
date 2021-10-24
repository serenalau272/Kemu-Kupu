package com.enums;

import java.io.IOException;

import com.MainApp;
import com.util.Sounds;
import com.models.GeneralModal;

/**
 * An enum to represent the status of a word being tested.
 */
public enum ConfirmModal implements GeneralModal {
    MENU, 
    INSTANTMENU,
    REPLAY, 
    SIGNOUT, 
    DELETE, 
    RESET;

    public String getMessage() {
        switch (this) {
            case MENU:
            case INSTANTMENU:
            case REPLAY:
                return "Are you sure you want to exit the game? Current results will be lost.";
            case SIGNOUT:
                return "Are you sure you would like to sign out?";
            case RESET:
                return "Are you sure you would like to reset all user statistics? You can't undo this action.";
            case DELETE:
                return "Are you sure you would like to delete your account? You can't undo this action.";
            default:
                System.err.println("ERROR: Confirmation type not implemented.");
                return "";
        }
    }

    public void doAction() {
        switch (this) {
            case INSTANTMENU:
            case MENU:
                MainApp.setRoot(Views.MENU);
                break;
            case REPLAY:
                MainApp.setRoot(Views.GAMEMODE);
                Sounds.playMusic("menu");
                break;
            case SIGNOUT:
                MainApp.getUser().signout();
                MainApp.setRoot(Views.MENU);
                break;
            case RESET:
                try {
                    String res = MainApp.getUser().resetAccount();
                    if (res == null) {
                        MainApp.setRoot(Views.PROFILE);
                    }
                } catch (IOException e) {
                    System.err.println("Unable to make request");
                }
                break;
            case DELETE:
                try {
                    String res = MainApp.getUser().deleteAccount();
                    if (res == null) {
                        MainApp.setRoot(Views.MENU);
                    }
                } catch (IOException e) {
                    System.err.println("Unable to make request");
                }
                break;
            default:
                System.err.println("ERROR: Confirmation type not implemented.");
                return;
        }
    }
}