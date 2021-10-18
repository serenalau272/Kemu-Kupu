package com.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;

import com.enums.Achievement;
import com.enums.Avatar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest extends User {
    private static String prefix;

    //// Helper Methods ////

    /**
     * Create a user account for testing purposes
     * @param name the name of the account
     * @return a logged in account
     */
    private User __createAccount(String name) {
        User user = new User();
        try {
            String res = user.signup(prefix + name, "testing123", "tester");
            if (res != null)
                fail("Failed to create account: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to create user due to error!");
        }
        return user;
    }

    /**
     * Runs the delete method on the user account.
     * @param user
     */
    private void __deleteAccount(User user) {
        if (!user.isLoggedIn())
            fail("Tried to delete a user account that was already deleted! This is likely a problem with a test.");
        try {
            String res = user.deleteAccount();
            if (res != null)
                fail("Failed to delete account: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Unable to delete user account: " + e.toString());
        }
    }

    //// Tests ////

    @BeforeAll
    public static void setup() {
        prefix = String.valueOf(Instant.now().toEpochMilli());
    }

    @Test
    public void testAccountCreation() {
        //Create a new account!
        User user = this.__createAccount("_testing_account_creation");

        //Check details are correct
        assertEquals(prefix + "_testing_account_creation", user.getUsername());
        assertEquals("tester", user.getNickname());
        assertEquals(1, user.getCostumes().size());
        assertTrue(user.getCostumes().contains(Avatar.DEFAULT));
        assertEquals(0, user.getAchievements().size());

        this.__deleteAccount(user);
    }

    
    public void testAccountLogin() {
        User createUser = this.__createAccount("_testing_account_login");
        //Create a new account!
        try {
            String res = createUser.signup(prefix + "_testing_acount_account_login", "testing123", "tester");
            if (res != null)
                fail("Failed to create account: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to create user due to error!");
        }

        //Test logging into that account
        User user = new User();
        try {
            String res = user.login(prefix + "_testing_acount_account_login", "testing123");
            if (res != null)
                fail("Failed to login to account: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to login to user account due to error!");
        }

        //Check details are correct
        assertEquals(prefix + "_testing_acount_account_creation", user.getUsername());
        assertEquals("tester", user.getNickname());
        assertEquals(1, user.getCostumes().size());
        assertTrue(user.getCostumes().contains(Avatar.DEFAULT));
        assertEquals(0, user.getAchievements().size());

        this.__deleteAccount(user);
    }

    @Test
    public void testAddingAchievement() {
        //Create Account
        User user = this.__createAccount("_testing_account_achievements");
        assertEquals(0, user.getAchievements().size());
        
        //Add achievement
        //TODO test all achievements in this manner
        Achievement achievement = Achievement.PARTICIPATION_PRACTICE;
        try {
            String res = user.unlockAchievement(achievement);
            if (res != null)
                fail("Failure to add achievement: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add achievement: " + e.toString());
        }

        //Validate achievement added
        assertEquals(1, user.getAchievements().size());
        assertTrue(user.getAchievements().contains(achievement));
        this.__deleteAccount(user);
    }

    @Test
    public void testAddingScore() {
        //Create account
        User user = this.__createAccount("_testing_acount_score");

        //Add one completed match
        try {
            String res = user.addScore(1, 2);
            if (res != null)
                fail("Failure to add score: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add score: " + e.toString());
        }
        assertEquals(1, user.getNumGamesPlayed());
        assertEquals(1, user.getHighScore());
        assertEquals(2, user.getTotalStars());

        //Add a second completed match
        try {
            String res = user.addScore(10, 3);
            if (res != null)
                fail("Failure to add score: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add score: " + e.toString());
        }
        assertEquals(2, user.getNumGamesPlayed());
        assertEquals(10, user.getHighScore());
        assertEquals(5, user.getTotalStars());

        //Add a third completed match
        try {
            String res = user.addScore(4, 6);
            if (res != null)
                fail("Failure to add score: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add score: " + e.toString());
        }
        assertEquals(3, user.getNumGamesPlayed());
        assertEquals(10, user.getHighScore());
        assertEquals(11, user.getTotalStars());

        //Delete account
        this.__deleteAccount(user);
    }

    @Test
    public void testAddingCostume() {
        User user = this.__createAccount("_testing_account_costume");
        try {
            String res = user.addScore(100000, 100000);
            if (res != null)
                fail("Failure to add score: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add score: " + e.toString());
        }
        assertEquals(1, user.getCostumes().size());

        //Add and validate a single costume
        //TODO test and validate all costumes in this manner
        Avatar avatar = Avatar.ALIEN;
        try {
            String res = user.unlockCostume(avatar);
            if (res != null)
                fail("Failure to add achievement: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add achievement: " + e.toString());
        }

        //Validate costume added
        assertEquals(2, user.getCostumes().size());
        assertTrue(user.getCostumes().contains(avatar));

        this.__deleteAccount(user);
    }

    @Test
    public void testSettingAvatar() {
        User user = this.__createAccount("_testing_account_set_costume");
        //Add score to the account
        try {
            String res = user.addScore(100000, 100000);
            if (res != null)
                fail("Failure to add score: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add score: " + e.toString());
        }
        //Add an avatar to the account
        Avatar avatar = Avatar.ALIEN;
        try {
            String res = user.unlockCostume(avatar);
            if (res != null)
                fail("Failure to add achievement: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add achievement: " + e.toString());
        }

        //Assert that the account is currently on the default avatar.
        assertEquals(Avatar.DEFAULT, user.getAvatar());

        //Update avatar
        try {
            String res = user.setAvatar(avatar);
            if (res != null)
                fail("Failure to add achievement: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add achievement: " + e.toString());
        }

        //Assert that avatar was updated correctly
        assertEquals(avatar, user.getAvatar());
    }
}
