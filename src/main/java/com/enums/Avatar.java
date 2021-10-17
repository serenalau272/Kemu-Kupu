package com.enums;

public enum Avatar {
    DEFAULT,
    SAILOR,
    MAGICIAN,
    WIZARD,
    NINJA,
    QUEEN,
    FAIRY,
    PROFESSOR,
    ALIEN,
    CHEF;

    /**
     * Convert a strin ginto an avatar, this is useful for parsing messages from the api into avatars.
     * @param s the string to parse to the avatar
     * @return an avatar fro the user
     */
    public static Avatar fromString(String s) {
        switch (s.strip().toLowerCase()) {
            case "b":
                return DEFAULT;
            case "sailor":
                return SAILOR;
            case "magician":
                return MAGICIAN;
            case "wizard":
                return WIZARD;
            case "ninja":
                return NINJA;
            case "queen":
                return QUEEN;
            case "fairy":
                return FAIRY;
            case "professor":
                return PROFESSOR;
            case "alien":
                return ALIEN;
            case "chef":
                return CHEF;
            default:
                System.err.println("Recieved unknown avatar request from string to avatar!");
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
                return "B";
            case SAILOR:
                return "Sailor";
            case MAGICIAN:
                return "Magician";
            case WIZARD:
                return "Wizard";
            case NINJA:
                return "Ninja";
            case QUEEN:
                return "Queen";
            case FAIRY:
                return "Fairy";
            case PROFESSOR:
                return "Professor";
            case ALIEN:
                return "Alien";
            case CHEF:
                return "Chef";
            default:
                System.err.println("Tried to convert unknown avatar into a string!");
                return "B";
        }
    }
}
