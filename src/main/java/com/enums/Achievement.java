package com.enums;

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

    public String getAchievementLabel(int level) {
        switch (this) {
            case EXPLORER:
                switch (level) {
                    case 1:
                        return "Play a Practice round";
                    case 2:
                        return "Play a Game round";
                    case 3: 
                        return "Play every topic";
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
