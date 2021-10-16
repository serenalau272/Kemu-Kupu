package com.enums;

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
}
