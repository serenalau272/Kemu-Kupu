package com.components;

import java.util.List;
import java.util.ArrayList;

import com.enums.Achievement;

public class AchievementItem {
    private Achievement achievement;
    private String typeName;
    private int max;
    private List<Integer> levels = new ArrayList<>();


    public AchievementItem(Achievement achievement, List<Integer> levels) {
        this.achievement = achievement;
        this.typeName = achievement.getTypeName();
        this.max = achievement.getTypeMax();
        this.levels = levels;
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
