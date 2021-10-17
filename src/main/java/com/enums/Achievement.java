package com.enums;

import javafx.fxml.LoadException;

public enum Achievement {
    UNLOCKCOSTUMES,
    PARTICIPATION_PRACTICE,
    PARTICIPATION_GAME,
    NUMGAMES_FIVE,
    NUMGAMES_TEN,
    NUMGAMES_TWENTY,
    NUMGAMES_FIFTY,
    NUMGAMES_HUNDRED,
    HIGHSCORE_SEVENTYFIVE,
    HIGHSCORE_NINETY,
    HIGHSCORE_HUNDRED,
    TIMER_FORTY,
    TIMER_THIRTY,
    TIMER_FIFTEEN,
    STAR_FIVE,
    STAR_TWENTYFIVE,
    STAR_SEVENTY,
    STAR_HUNDRED;

    public int getId(){
        switch (this) {
            case PARTICIPATION_PRACTICE:
                return 11;
            case PARTICIPATION_GAME:
                return 12;
            case NUMGAMES_FIVE:
                return 21;
            case NUMGAMES_TEN:
                return 22;
            case NUMGAMES_TWENTY:
                return 23;
            case NUMGAMES_FIFTY:
                return 24; 
            case NUMGAMES_HUNDRED:
                return 25;    
            default:
                return -1;
        }
    }
        /**
     * Convert a string into an achievement, this is useful for parsing messages from the api into achievement.
     * @param s the string to parse to the achievement
     * @return an achievement for the user
     * @throws LoadException if the string can't be mapped to an achievement
     */
    public static Achievement fromString(String s) throws LoadException {
        switch (s.strip().toLowerCase()) {
            case "practice":
                // return ;
            case "play":
                // return ;
            case "practiceEvery":
                // return ;
            case "playEvery":
                // return ;
            case "diligence5":
                // return ;
            case "diligence10":
                // return ;
            case "diligence20":
                // return ;
            case "diligence50":
                // return ;
            case "diligence100":
                // return ;
            case "highscore75":
                // return ;
            case "highscore90":
                // return ;
            case "highscore100":
                // return ;
            case "unlockAll":
                //return ;
            case "star10":
                // return ;
            case "star50":
                // return ;
            case "star100":
                // return ;
            case "star200":
                // return ;
            case "star300":
                // return ;
            default:
                System.err.println();
                throw new LoadException("Recieved unknown achievement request from string to avatar! Offending string " + s);
        }
    }
    
    /**
     * Converts an achievement into a string, this is useful for communication with the api.
     * @return a string representing this achievement
     */
    public String toString() {
        System.err.println("Tried to convert unknown avatar into a string!");
        //TODO
        //Should convert into the *exact* same strings as can be seen above in fromString();
        return "practice";
    }
}
