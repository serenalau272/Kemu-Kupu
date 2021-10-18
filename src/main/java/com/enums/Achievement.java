package com.enums;

import javafx.fxml.LoadException;

public enum Achievement {
    EXPLORER,
    STUDENT,
    ACHIEVER,
    SPEEDY,
    POCKETS,
    STYLISH;

    public String toString(int level){
        String label;
        switch (this) {
            case EXPLORER:
                label = "EXPLORER";
                break;
            case STUDENT:
                label = "STUDENT";
                break;
            case ACHIEVER:
                label = "ACHIEVER";
                break;
            case SPEEDY:
                label = "SPEEDY";
                break;
            case POCKETS:
                label = "POCKETS";
                break;
            case STYLISH:
                label = "STYLISH";
                break;
            default:
                System.err.println("ERROR: Type not implemented for achievements!");
                return "";
        }
        String ret = label + "_" + Integer.toString(level);
        System.out.println(ret);
        return ret;
    }

    public static int getLevelFromString(String s){
        String[] components = s.split("_"); 
        return Integer.parseInt(components[1]);
    }

    public static Achievement getAchievementTypeFromString(String s){
        String[] components = s.split("_");            
        switch (components[0].strip().toUpperCase()) {
            case "EXPLORER":
                return EXPLORER;
            case "STUDENT":
                return STUDENT;
            case "ACHIEVER":
                return ACHIEVER;
            case "SPEEDY":
                return SPEEDY;
            case "POCKETS":
                return POCKETS;
            case "STYLISH":
                return STYLISH;
            default:
                System.err.println("ERROR: Invalid string. No avatar found");
                return null;
        }
    }

    public String getTypeName() {
        switch (this) {
            case EXPLORER:
                return "Big Explorer";
            case STUDENT:
                return "Diligent Student";
            case ACHIEVER:
                return "High Achiever";
            case SPEEDY:
                return "Speedy Speller";
            case POCKETS:
                return "Deep Pockets";
            case STYLISH:
                return "Stylish Bee";
            default:
                System.err.println("ERROR: Type not implemented for achievements!");
                return "";
        }
    }

    public int getTypeMax() {
        switch (this) {
            case EXPLORER:
                return 3;
            case STUDENT:
                return 5;
            case ACHIEVER:
                return 3;
            case SPEEDY:
                return 3;
            case POCKETS:
                return 5;
            case STYLISH:
                return 1;
            default:
                System.err.println("ERROR: Type not implemented for achievements!");
                return -1;
        }
    }
    /**
     * Convert a string into an achievement, this is useful for parsing messages from the api into achievement.
     * @param s the string to parse to the achievement
     * @return an achievement for the user
     * @throws LoadException if the string can't be mapped to an achievement
     */
    public static String fromString(String s) throws LoadException {
        switch (s.strip().toLowerCase()) {
            case "practice":
                return "EXPLORER_1";
            case "play":
                return "EXPLORER_2";
            case "playEvery":
                return "EXPLORER_3";
            case "diligence5":
                return "STUDENT_1";
            case "diligence10":
                return "STUDENT_2";
            case "diligence20":
                return "STUDENT_3";
            case "diligence50":
                return "STUDENT_4";
            case "diligence100":
                return "STUDENT_5";
            case "highscore75":
                return "ACHIEVER_1";
            case "highscore90":
                return "ACHIEVER_2";
            case "highscore100":
                return "ACHIEVER_3";
            case "unlockAll":
                return "STYLISH_1";
            case "star10":
                return "POCKETS_1";
            case "star50":
                return "POCKETS_2";
            case "star100":
                return "POCKETS_3";
            case "star200":
                return "POCKETS_4";
            case "star300":
                return "POCKETS_5";
            default:
                System.err.println();
                throw new LoadException("Recieved unknown achievement request from string to achievement! Offending string " + s);
        }
    }
    
    /**
     * Converts an achievement into a string, this is useful for communication with the api.
     * @return a string representing this achievement
     * @throws LoadException
     */
    public String toString(String s) throws LoadException {
        switch (s) {
            case "EXPLORER_1":
                return "practice";
            case "EXPLORER_2":
                return "play";
            case "EXPLORER_3":
                return "playEvery";
            case "STUDENT_1":
                return "diligence5";
            case "STUDENT_2":
                return "diligence10";
            case "STUDENT_3":
                return "diligence20";
            case "STUDENT_4":
                return "diligence50";
            case "STUDENT_5":
                return "diligence100";
            case "ACHIEVER_1":
                return "highscore75";
            case "ACHIEVER_2":
                return "highscore90";
            case "ACHIEVER_3":
                return "highscore100";
            case "STYLISH_1":
                return "unlockAll";
            case "POCKETS_1":
                return "star10";
            case "POCKETS_2":
                return "star50";
            case "POCKETS_3":
                return "star100";
            case "POCKETS_4":
                return "star200";
            case "POCKETS_5":
                return "star300";
            default:
                System.err.println();
                throw new LoadException("Recieved unknown achievement request from string to avatar! Offending string " + s);
        }
    }

    public String getAchievementLabel(int level) {
        switch (this) {
            case EXPLORER:
                switch (level) {
                    case 1:
                        return "Play a Practice round";
                    case 2:
                        return "Play a Game round";
                    case 3: 
                        return "Play Game for all topics";
                    default:
                        System.err.println("ERROR: Level not implemented for this type.");
                        return "";
                }
            case STUDENT:
                switch (level) {
                    case 1: 
                        return "Play 5 games";
                    case 2:
                        return "Play 10 games";
                    case 3:
                        return "Play 20 games";
                    case 4:
                        return "Play 50 games";
                    case 5:
                        return "Play 100 games";
                    default:
                        System.err.println("ERROR: Level not implemented for this type.");
                        return "";
                }
            case ACHIEVER:
                switch (level) {
                    case 1: 
                        return "Get a score of 75";
                    case 2:
                        return "Get a score of 90";
                    case 3:
                        return "Get a score of 100";
                    default:
                        System.err.println("ERROR: Level not implemented for this type.");
                        return "";
                }
            case SPEEDY:
                switch (level) {
                    case 1:
                        return "Score 100 on a 40s timer";
                    case 2: 
                        return "Score 100 on a 30s timer";
                    case 3: 
                        return "Score 100 on a 15s timer";
                    default:
                        System.err.println("ERROR: Level not implemented for this type.");
                        return "";
                }
            case POCKETS:
                switch (level) {
                    case 1:
                        return "Reach 10 stars in total";
                    case 2:
                        return "Reach 50 stars in total";
                    case 3:
                        return "Reach 100 stars in total";
                    case 4:
                        return "Reach 200 stars in total";
                    case 5:
                        return "Reach 300 stars in total";
                    default:
                        System.err.println("ERROR: Level not implemented for this type.");
                        return "";
                }
            case STYLISH:
                return "Unlock all costumes";
            default:
                System.err.println("ERROR: Type not implemented for achievements!");
                return "";
        }
    }
}
