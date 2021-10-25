package com.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import com.enums.Avatar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
            fail("Failed to create user due to error: " + e.toString());
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
    
    @Test
    public void testAccountLogin() {
        User createUser = this.__createAccount("_testing_account_login");

        //Test logging into that account
        User user = new User();
        try {
            String res = user.login(prefix + "_testing_account_login", "testing123");
            if (res != null)
                fail("Failed to login to account: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to login to user account due to error!");
        }

        //Check details are correct
        assertEquals(prefix + "_testing_account_login", user.getUsername());
        assertEquals("tester", user.getNickname());
        assertEquals(1, user.getCostumes().size());
        assertTrue(user.getCostumes().contains(Avatar.DEFAULT));
        assertEquals(0, user.getAchievements().size());

        this.__deleteAccount(createUser);
    }
    
    @Test
    public void testAddingAchievement() {
        //Create Account
        User user = this.__createAccount("_testing_account_achievements");
        assertEquals(0, user.getAchievements().size());
        
        //Add achievement
        //TODO test all achievements in this manner
        String achievement = "EXPLORER_2";
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
    
    @Disabled
    @Test
    public void testAddingAchievementExtended() {
        //This is the extended version of testAddingAchievement, which tests literally every possible achievement.
        //TODO
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
    
    @Disabled
    @Test
    public void testAddingCostumeExtended() {
        //This is the extended version of testAddingCostume, which tests literally every possible costume.
        //TODO
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
        assertEquals(Avatar.DEFAULT, user.getSelectedAvatar());

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
        assertEquals(avatar, user.getSelectedAvatar());

        //Cleanup
        this.__deleteAccount(user);
    }
    
    @Test
    public void testChangingNickname() {
        User user = this.__createAccount("_testing_account_change_nickname");
        assertEquals("tester", user.getNickname());

        try {
            String res = user.setNickname("newnickname");
            if (res != null)
                fail("Failed to change nickname: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to change nickname: " + e.toString());
        }

        assertEquals("newnickname", user.getNickname());
        this.__deleteAccount(user);
    }
    
    @Test
    public void testChangingUsername() {
        User user = this.__createAccount("_testing_account_change_username");
        assertEquals(prefix + "_testing_account_change_username", user.getUsername());
        
        try {
            String res = user.setUsername(prefix + "_newusername123");
            if (res != null)
                fail("Failed to change nickname: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to change nickname: " + e.toString());
        }

        assertEquals(prefix + "_newusername123", user.getUsername());
        this.__deleteAccount(user);
    }

    @Test
    public void testResettingAccount() {
        User user = this.__createAccount("_testing_account_reset_account");
        //Check baseline
        assertEquals(Avatar.DEFAULT, user.getSelectedAvatar());
        assertEquals(1, user.getCostumes().size());
        assertEquals(0, user.getAchievements().size());
        assertEquals(0, user.getHighScore());
        assertEquals(0, user.getNumGamesPlayed());

        //Add achievements, add scores, add costume, set costume
        try {
            String res;
            res = user.unlockAchievement("STUDENT_1");
            if (res != null)
                fail("Failed to unlock achievement: " + res);
            res = user.unlockAchievement("STUDENT_2");
            if (res != null)
                fail("Failed to unlock achievement: " + res);
            res = user.unlockAchievement("STUDENT_3");
            if (res != null)
                fail("Failed to unlock achievement: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add achievements: " + e.toString());
        }

        try {
            String res = user.addScore(9999, 10000);
            if (res != null)
                fail("Failed to add score: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to add score: " + e.toString());
        }

        try {
            String res = user.unlockCostume(Avatar.ALIEN);
            if (res != null)
                fail("Failed to unlock costume: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to unlock costume: " + e.toString());
        }

        try {
            String res = user.setAvatar(Avatar.ALIEN);
            if (res != null)
                fail("Failed to unlock costume: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to set costume: " + e.toString());
        }

        //Validate things were changed properly
        assertEquals(Avatar.ALIEN, user.getSelectedAvatar());
        assertEquals(3, user.getAchievements().size());
        assertTrue(user.getAchievements().contains("STUDENT_1"));
        assertTrue(user.getAchievements().contains("STUDENT_2"));
        assertTrue(user.getAchievements().contains("STUDENT_3"));
        assertEquals(9999, user.getHighScore());
        assertEquals(1, user.getNumGamesPlayed());
        assertEquals(2, user.getCostumes().size());

        //Reset
        try {
            String res = user.resetAccount();
            if (res != null)
                fail("Failed to reset user: " + res);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Critical failure to reset account: " + e.toString());
        }

        //Validate back to default
        assertEquals(Avatar.DEFAULT, user.getSelectedAvatar());
        assertEquals(0, user.getAchievements().size());
        assertEquals(0, user.getHighScore());
        assertEquals(0, user.getNumGamesPlayed());
        assertEquals(1, user.getCostumes().size());

        this.__deleteAccount(user);
    }

    @Test
    public void testSerialization() {
        //HACK these are combined into one file as currently the new User(); method just guesses which to serialize from 
        //which could cause a collision if tested in a bad order. Combining them ensures serial testing.

        //Test Guest Serialization
        {
            new File(this.guestSavePath).delete();
            new File(this.userSavePath).delete();
        
            User user = new User(); //Create guest account
            assertEquals(0, user.getAchievements().size());
            try {
                String res = user.unlockAchievement("STUDENT_1");
                if (res != null)
                    fail("Failed to unlock achievement: " + res);
            } catch (IOException e) {
                e.printStackTrace();
                fail("Critical failure to unlock achievement: " + e.toString());
            }
    
            assertTrue(user.getAchievements().contains("STUDENT_1"));
    
            User newUser = new User();
            assertTrue(newUser.getAchievements().contains("STUDENT_1"));
            assertFalse(newUser.isLoggedIn());
        }

        //Test API Serialization
        {
            new File(this.guestSavePath).delete();
            new File(this.userSavePath).delete();
            User user = this.__createAccount("_testing_account_serialize_logged_in");
            try {
                String res = user.unlockAchievement("STUDENT_2");
                if (res != null)
                    fail("Failed to unlock achievement: " + res);
            } catch (IOException e) {
                e.printStackTrace();
                fail("Critical failure to unlock achievement: " + e.toString());
            }
    
            assertTrue(user.getAchievements().contains("STUDENT_2"));
    
            User newUser = new User();
            assertTrue(newUser.getAchievements().contains("STUDENT_2"));
            assertTrue(newUser.isLoggedIn());
    
            this.__deleteAccount(user);
        }
    }
}
