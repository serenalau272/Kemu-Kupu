package com.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.fxml.LoadException;

public class AchievementTest {
    @Test
    public void testStringConversionMethods() {
        String[] achievements = {
            "practice",
            "play",
            "playEvery",
            "diligence5",
            "diligence10",
            "diligence20",
            "diligence50",
            "diligence100",
            "highscore75",
            "highscore90",
            "highscore100",
            "unlockAll",
            "star10",
            "star50",
            "star100",
            "star200",
            "star300"
        };

        for (String achievement : achievements) {
            String converted = "";
            try {
                converted = Achievement.fromString(achievement);
            } catch (LoadException e) {
                e.printStackTrace();
                fail("Failed to load from string, error: " + e.toString());
            }

            try {
                assertEquals(achievement, Achievement.toString(converted));
            } catch (LoadException e) {
                e.printStackTrace();
                fail("Failed to load from string, error: " + e.toString());
            }
        }
    }

    @Test
    public void testGetTypeName() {
        List<Achievement> achievements = Arrays.asList(Achievement.values());
        for (Achievement achievement : achievements) {
            assertNotEquals("", achievement.getTypeName());
        }
    }

    @Test
    public void testGetTypeMax() {
        List<Achievement> achievements = Arrays.asList(Achievement.values());
        for (Achievement achievement : achievements) {
            assertNotEquals(-1, achievement.getTypeName());
        }
    }

    @Test
    public void testGetLevelFromString() {
        assertEquals(2, Achievement.getLevelFromString("friend_2"));
        assertEquals(0, Achievement.getLevelFromString("friend_0"));
        assertEquals(99923, Achievement.getLevelFromString("friend_99923"));
    }

    @Test
    public void testGetAchievementTypeFromString() {
        List<Achievement> achievements = Arrays.asList(Achievement.values());
        for (Achievement achievement : achievements) {
            String internal = achievement.toString(1);
            assertNotEquals("", internal);
            Achievement converted = Achievement.getAchievementTypeFromString(internal);
            assertNotEquals(null, converted);
            assertEquals(achievement, converted);
        }
    }

    @Test
    public void testGetAchievementLabel() {
        List<Achievement> achievements = Arrays.asList(Achievement.values());
        for (Achievement achievement : achievements) {
            for (int i = 1; i <= achievement.getTypeMax(); i++) {
                if (achievement.getAchievementLabel(i) == "") {
                    fail("getAchievementLabel failed on: " + achievement.toString(i));
                }
            }
        }
    }
}
