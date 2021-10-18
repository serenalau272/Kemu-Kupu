package com.enums;

public enum Avatar {
    DEFAULT,
    SAILOR,
    TUXEDO,
    WIZARD,
    NINJA,
    PRINCESS,
    FAIRY,
    NASSER,
    ALIEN,
    CHEF;

    /**
     * Convert a strin ginto an avatar, this is useful for parsing messages from the api into avatars.
     * @param s the string to parse to the avatar
     * @return an avatar fro the user
     */
    public static Avatar fromString(String s) {
        switch (s.strip().toLowerCase()) {
            case "default":
                return DEFAULT;
            case "sailor":
                return SAILOR;
            case "magician":
                return TUXEDO;
            case "wizard":
                return WIZARD;
            case "ninja":
                return NINJA;
            case "queen":
                return PRINCESS;
            case "fairy":
                return FAIRY;
            case "professor":
                return NASSER;
            case "alien":
                return ALIEN;
            case "chef":
                return CHEF;
            default:
                System.err.println("Recieved unknown avatar request from string to avatar! Affected avatar string " + s);
                return DEFAULT;
        }
    }
    
    /**
     * converts an avatar into a string, this is useful for communication with the api.
     * @return a string representing this avatar
     */
    public String toString() {
        switch (this) {
            case DEFAULT:
                return "default";
            case SAILOR:
                return "sailor";
            case TUXEDO:
                return "magician";
            case WIZARD:
                return "wizard";
            case NINJA:
                return "ninja";
            case PRINCESS:
                return "queen";
            case FAIRY:
                return "fairy";
            case NASSER:
                return "professor";
            case ALIEN:
                return "alien";
            case CHEF:
                return "chef";
            default:
                System.err.println("Tried to convert unknown avatar into a string!");
                return "B";
        }
    }
}
