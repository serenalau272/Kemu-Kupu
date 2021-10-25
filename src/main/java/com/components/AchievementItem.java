package com.components;

import java.util.List;
import java.util.ArrayList;

import com.enums.Achievement;

/**
 * AchievementItem is an object used to store all the information regarding one achievement type. This information is used 
 * to create the each type component that is dynamically generated and added to the achievements screen.
 */

public class AchievementItem {
    private Achievement achievement;
    private String typeName;
    private int max;
    private List<Integer> levels = new ArrayList<>();

    public AchievementItem(Achievement achievement, List<Integer> levels) {
        this.achievement = achievement;
        this.typeName = achievement.getTypeName();
        this.max = achievement.getTypeMax();
        this.levels = levels; // this List contains the levels that have been unlocked for this achievement type
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public int getMax() {
        return max;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<Integer> getLevels() {
        return levels;
    }

}
