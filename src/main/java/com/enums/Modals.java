package com.enums;

public enum Modals {
    SETTING,
    ATTRIBUTION,
    PAUSE,
<<<<<<< HEAD
    HELP;
=======
    HELP,
    ACHIEVEMENT,
    SHOP,
    PROFILE;
>>>>>>> a21114531d1ff5eb85612c38456248976e166f5f

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
<<<<<<< HEAD
=======
            case PROFILE: {
                return "Profile";
            }
            case ACHIEVEMENT: {
                return "Achievement";
            }
            case SHOP: {
                return "Shop";
            }
>>>>>>> a21114531d1ff5eb85612c38456248976e166f5f
            default: {
                System.err.println("ERROR (Modals.java): Type not implemented for getting file name!");
                return "Menu";
            }
        }
    }

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
<<<<<<< HEAD
=======
            case PROFILE: {
                return "User Profile";
            }
            case SHOP: {
                return "Kemu Kupu - Costume Shop";
            }
>>>>>>> a21114531d1ff5eb85612c38456248976e166f5f
            default: {
                System.err.println("ERROR (modals.java): Type not implemented for getting window name!");
                return "Kemu Kupu";
            }
        }
    }
}
