package com.models;

import java.util.Map;
import java.util.HashMap;

import com.enums.Avatar;

import java.util.AbstractMap;

/**
 * This class handles the storing and retreiving of the current user.
 */
public class User {
    private String name;
    private Avatar selectedAvatar;
    private Map<Avatar, Boolean> boughtAvatars = new HashMap<Avatar, Boolean>();
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

    User(){
        //guest
        this.name = "";
        this.selectedAvatar = Avatar.DEFAULT;
        this.boughtAvatars = null;
        this.stars = null;
        this.highScore = null;
    }

    public void login(String name, String password){
        //verify and retrieve all fields

    }

    public void signup(String name, String password){
        //verify u can create this user
        this.name = name;
        this.stars = 0;
        this.highScore = 0;
        this.boughtAvatars = Map.ofEntries(
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.DEFAULT, true),
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.NASSER, false),
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.ALIEN, false),
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.CHEF, false),
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.FAIRY, false),
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.NINJA, false),
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.PRINCESS, false),
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.SAILOR, false),
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.TUXEDO, false),
            new AbstractMap.SimpleEntry<Avatar, Boolean>(Avatar.WIZARD, false)
        );

        //post all fields

    }
}
