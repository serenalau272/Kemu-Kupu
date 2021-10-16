package com.models;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.enums.Avatar;

import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * This class handles the storing and retreiving of the current user.
 */
public class User {
    private String name;
    private Avatar selectedAvatar;
    private HashSet<Avatar> boughtAvatars = new HashSet<>();
    private Integer stars;
    private Integer highScore;

    private final Map<Avatar, Integer> costAvatars = Map.ofEntries(
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.DEFAULT, 0),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.ALIEN, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.CHEF, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.FAIRY, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.NINJA, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.PRINCESS, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.SAILOR, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.TUXEDO, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.WIZARD, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.NASSER, 100)
    );

    User() {
        //guest
        this.name = "";
        this.selectedAvatar = Avatar.DEFAULT;
        this.boughtAvatars = null;
        this.stars = null;
        this.highScore = null;
    }

    /**
     * Login a user to the api
     * @param name the username of the account
     * @param password the password of the account
     * @throws Exception if unable to complete the request
     */
    public void login(String name, String password) throws Exception {
        //verify and retrieve all fields
    }

    /**
     * Create a new user with the api, and log them in automatically.
     * @param name the username
     * @param password the password to signup with
     * @throws Exception if unable to complete the request
     */
    public void signup(String name, String password) throws Exception {
        //verify u can create this user
        this.name = name;
        this.stars = 0;
        this.highScore = 0;
        this.boughtAvatars = new HashSet<Avatar>() {
            {
                add(Avatar.WIZARD);
            }
        };

        //post all fields
    }

    /**
     * Save a new score for this user, should be called at the end of the quiz.
     * @param score the score to be saved
     * @throws Exception throws if unable to complete the request
     */
    public void addScore(int score) throws Exception {

        //Unable to add score
    }

    /**
     * Collect this users scores from the api
     * @return a list of this users scores from the api
     * @throws Exception if unable to complete request
     */
    public List<Integer> getScores() throws Exception {
        List<Integer> arr = new ArrayList<Integer>() {
            {
                add(15);
                add(20);
                add(60);
                add(75);
                add(90);
            }
        };
        return arr;
    }

    /**
     * Request this users list of unlocked costumes from the api.
     * @return a hashset containing all of the users bought costumes
     * @throws Exception if unable to complete the request.
     */
    public HashSet<Avatar> getCostumes() throws Exception {
        return new HashSet<Avatar>() {
            {
                add(Avatar.PRINCESS);
                add(Avatar.WIZARD);
                add(Avatar.DEFAULT);
            }
        };
    }

    /**
     * Request the api to unlock a costume for this user
     * @throws Exception if unable to complete the request
     */
    public void unlockCostume() throws Exception {

    }
}
