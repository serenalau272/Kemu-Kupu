package com.se206.g11.enums;

/**
 * Represents the different views that may be loaded as fxml pages, to limit the bugs that may appear
 */
public enum View {
    MENU,
    TOPIC,
    QUIZ,
    EXIT,
    RESULTS;

    public String getFileName() {
        switch (this) {
            case MENU: {
                return "Menu";
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
            case TOPIC: {
                return "Kemu Kupu - Choose a Topic!";
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
            default: {
                System.err.println("ERROR (modals.java): Type not implemented for getting window name!");
                return "Kemu Kupu";
            }
        }
    }
}