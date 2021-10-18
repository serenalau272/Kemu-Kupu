package com.enums;

/**
 * Represents the different views that may be loaded as fxml pages, to limit the bugs that may appear
 */
public enum View {
    MENU,
    GAMEMODE,
    TOPIC,
    QUIZ,
    EXIT,
    RESULTS,
    REWARD,
    PROFILE,
    SHOP,
    WHEEL,
    ACHIEVEMENT;

    public String getFileName() {
        switch (this) {
            case MENU: {
                return "Menu";
            }
            case GAMEMODE: {
                return "GameMode";
            }
            case TOPIC: {
                return "Topic";
            }
            case QUIZ: {
                return "Quiz";
            }
            case EXIT: {
                return "Exit";
            }
            case RESULTS: {
                return "Results";
            }
            case REWARD: {
                return "Reward";
            }
            case WHEEL: {
                return "Wheel";
            }
            case PROFILE: {
                return "Profile";
            }
            case SHOP: {
                return "Shop";
            }
            case ACHIEVEMENT: {
                return "Achievements";
            }
            default: {
                System.err.println("ERROR (Modals.java): Type not implemented for getting file name!");
                return "Menu";
            }
        }
    }

    public String getWindowName() {
        switch (this) {
            case MENU: {
                return "Kemu Kupu";
            }
            case GAMEMODE: {
                return "Kemu Kupu - Select Game Mode";
            }
            case TOPIC: {
                return "Kemu Kupu - Choose a Topic!";
            }
            case WHEEL: {
                return "Spin to Win!";
            }
            case QUIZ: {
                return "Kemu Kupu - Let's Play!";
            }
            case EXIT: {
                return "Kemu Kupu - Goodbye!";
            }
            case RESULTS: {
                return "Kemu Kupu - Results";
            }
            case REWARD: {
                return "Kemu Kupu - Reward";
            }
            case PROFILE: {
                return "Kemu Kupu - User Profile";
            }
            case SHOP: {
                return "Kemu Kupu - Costume Shop";
            }
            case ACHIEVEMENT: {
                return "Kemu Kupu - Achievements!";
            }
            default: {
                System.err.println("ERROR (modals.java): Type not implemented for getting window name!");
                return "Kemu Kupu";
            }
        }
    }
}