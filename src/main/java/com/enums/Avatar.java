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

    public String getName() {
        switch (this) {
            case DEFAULT: {
                return "B";
            }
            case SAILOR: {
                return "Sailor";
            }
            case MAGICIAN: {
                return "Magician";
            }
            case WIZARD: {
                return "Wizard";
            }
            case NINJA: {
                return "Ninja";
            }
            case QUEEN: {
                return "Queen";
            }
            case FAIRY: {
                return "Fairy";
            }
            case PROFESSOR: {
                return "Professor";
            }
            case ALIEN: {
                return "Alien";
            }
            case CHEF: {
                return "Chef";
            }
            default: {
                System.err.println("ERROR (Avatar.java): Type not implemented for getting avatar name!");
                return "B";
            }
        }
    }
}
