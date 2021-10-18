package com.models;

import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import java.util.HashSet;
import java.util.List;

import com.enums.Achievement;
import com.enums.Avatar;
import com.google.gson.Gson;

import javafx.fxml.LoadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * Used internally to define allowed interaction methods with the api.
 */
enum RequestMethod {
    Post,
    Get,
    Delete;
    public String toString() {
        switch (this) {
            case Post:
                return "POST";
            case Get:
                return "GET";
            case Delete:
                return "DELETE";
        }
        return null;
    }
}

/**
 * Used internally to define accepted vs failure from the api.
 */
enum ResponseStatus {
    Success,
    Failure;
}

/**
 * Represents a simple api response, containing relevant data and methods
 */
class Response {
    private String body;
    private String path;
    private ResponseStatus status;

    Response(String data, ResponseStatus status, String path) throws IllegalArgumentException {
        if (data == null) 
            throw new IllegalArgumentException("Data may not be null");
        if (path == null)
            throw new IllegalArgumentException("Path may not be null");
        this.body = data;
        this.status = status;
        this.path = path;
    }

    /**
     * Parse the response body from json to it's contents
     * @return the parsed json data as a string, can be coreced into an object if needed using gson
     */
    public String loadJsonData() {
        return this.body.substring(10, this.body.length()-2);
    }

    /**
     * Parse the response as raw json, rather than just the raw string. This shoudl be used if there is a sub
     * object that needs to be parsed.
     * @return a string to be parsed.
     */
    public String loadJsonDataRaw() {
        return this.body.substring(9, this.body.length()-1);
    }

    public String getBody() {
        return this.body;
    }

    public String getPath() {
        return this.path;
    }

    public ResponseStatus getStatus() {
        return this.status;
    }
}

/**
 * One of many classes, exclusively used by gson to parse from a string into a java class for use internally.
 * This one is designed to be constructed to represent a costume from json.
 */
class JsonCostume {
    public String name;
    public String description;
    public Integer price;
}

/**
 * One of many classes, exclusively used by gson to parse from a string into a java class for use internally.
 * This one is designed to be constructed from a 200 response from GET /student endpoint.
 */
class JsonStudent {
    public Integer id;
    public String usr;
    public String current_costume;
    public List<JsonCostume> costumes;
}

/**
 * One of many classes, exclusively used by gson to parse from a string into a java class for use internally.
 * This one represents a score from the api
 */
class JsonScore {
    public String usr;
    public Integer score;
    public Integer num_stars;
    public Integer usr_id;
}

/**
 * One of many classes, exclusively used by gson to parse from a string into a java class for use internally.
 * This one represents a collection of scores from the api
 */
class JsonScores {
    public List<JsonScore> data;    
}

/**
 * This class handles the storing and retreiving of the current user.
 */
public class User {
    private final String apiPath = "https://kemukupu.com/api/v1";
    // private final String apiPath = "http://127.0.0.1:8000/api/v1";
    private String JWTToken;
    private String username;
    private Integer id;
    private Avatar selectedAvatar = Avatar.DEFAULT;
    private HashSet<Avatar> unlockedAvatars = new HashSet<>() { { add(Avatar.DEFAULT); } };
    private Integer highScore;
    private Integer totalStars;
    private Integer numGamesPlayed;

    //TODO implement
    private String nickname;
    private ArrayList<String> procuredAchievements = new ArrayList<>();

    private final Map<Avatar, Integer> costAvatars = Map.ofEntries(
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.DEFAULT, 0),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.ALIEN, 50),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.CHEF, 20),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.FAIRY, 80),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.NINJA, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.QUEEN, 30),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.SAILOR, 5),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.MAGICIAN, 30),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.WIZARD, 5),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.PROFESSOR, 200)
    );

    /**
     * A helper method for making http requests to the kemukupu api
     * Docs: https://kemukupu.com/api/docs
     * @param method the http method to use
     * @param path the subpath to request to
     * @param data the body of the request
     * @return a response instance.
     * @throws IllegalArgumentException thrown when one of the input paramaters is incorrect.
     * @throws IOException thrown when the service is unable to contact the server.
     */
    private Response __makeRequest(RequestMethod method, String path, String data) throws IllegalArgumentException, IOException {
        if (path == null) throw new IllegalArgumentException("Path must be set for request");
        if (method == null) throw new IllegalArgumentException("Method must be set");
        if (data == null && method == RequestMethod.Post) throw new IllegalArgumentException("When posting data cannot be null");
        
        //Establish connection
        String url = apiPath + path;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method.toString());
        connection.setDoOutput(true);

        //If we have an auth header, set it
        if (this.JWTToken != null) {
            connection.addRequestProperty("Authorisation", this.JWTToken);
        }

        //Add body if post request
        if (method == RequestMethod.Post) {
            byte[] out = data.getBytes(StandardCharsets.UTF_8);
            connection.setFixedLengthStreamingMode(out.length);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.connect();
            try (OutputStream os = connection.getOutputStream()) {
                os.write(out);
                os.close();
            }
        } else {
            connection.connect();
        }

        //Process Response
        int responseCode = connection.getResponseCode();
        InputStream inputStream;
        if (200 <= responseCode && responseCode <= 299) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder response = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null) 
            response.append(currentLine);
        in.close();

        return new Response(
            response.toString(), 
            (200 <= responseCode && responseCode <= 299) ? ResponseStatus.Success : ResponseStatus.Failure, 
            path
        );        
    }

    /**
     * After user has logged in, or been created, this method loads their data from the api.
     * @throws IOException if unable to contact the api
     */
    private void __loadData() throws IOException {
        //Load response and parse json
        Response res = this.__makeRequest(RequestMethod.Get, "/student", null);
        JsonStudent student = new Gson().fromJson("{" + res.loadJsonData() + "}", JsonStudent.class);
        
        //Set id
        this.id = student.id;

        //Set selected avatar
        this.selectedAvatar = Avatar.fromString(student.current_costume);

        //TODO Set nickname
        
        //Set unlocked avatars
        HashSet<Avatar> avatarUpdate = new HashSet<>();
        for (JsonCostume avatarName : student.costumes) {
            Avatar newAvatar = Avatar.fromString(avatarName.name);
            if (!avatarUpdate.contains(newAvatar))
                avatarUpdate.add(newAvatar);
        }
        this.unlockedAvatars = avatarUpdate;

        //Load and process score data
        res = this.__makeRequest(RequestMethod.Get, "/scores?id="+ this.id, null);
        JsonScores scores = new Gson().fromJson(res.getBody(), JsonScores.class);

        this.numGamesPlayed = scores.data.size();

        int newHighScore = Integer.MIN_VALUE;
        int newTotalStars = 0;
        for (JsonScore score : scores.data) {
            totalStars += score.num_stars;
            if (score.score > highScore)
                highScore = score.score;
        }
        this.totalStars = newTotalStars;
        this.highScore = newHighScore;

        //TODO Load and Process Achievements

        
    }

    public User() {
        this.username = "";
        this.totalStars = 24;
        this.highScore = 70;
        this.nickname = "George";
        this.procuredAchievements.add(Achievement.EXPLORER.toString(1));
    }

    /**
     * Create a new user with the api, and log them in automatically.
     * @param username the username
     * @param password the password to signup with
     * @throws Exception if unable to complete the request
     */
    public String signup(String username, String password) throws Exception {
        String body = "{\"usr\":\"" + username + "\",\"pwd\":\"" + password + "\"}";
        Response res = this.__makeRequest(RequestMethod.Post, "/student/create", body);
        if (res.getStatus() == ResponseStatus.Success) {
            //Succesful login, lets go from here.
            this.username = username;
            this.JWTToken = res.loadJsonData();
            //Load user data
            this.__loadData();
            return null;
        }
        return res.loadJsonData();
    }

    /**
     * Login a user to the api
     * @param username the username of the account
     * @param password the password of the account
     * @throws IOException if unable to complete the request
     */
    public String login(String username, String password) throws IOException {
        String body = "{\"usr\":\"" + username + "\",\"pwd\":\"" + password + "\"}";
        Response res = this.__makeRequest(RequestMethod.Post, "/student/login", body);
        if (res.getStatus() == ResponseStatus.Success) {
            //Succesful login, lets go from here.
            this.username = username;
            this.JWTToken = res.loadJsonData();
            //Load user data
            this.__loadData();
            return null;
        }
        return res.loadJsonData();
    }

    /** 
     * Save a new score for this user, should be called at the end of the quiz.
     * @param score the score to be saved
     * @return null if success, string with error to be displayed to user otherwise
     * @throws IOException throws if unable to complete the request
     * @throws LoadException if the user is not logged in
     */
    public String addScore(int score, int numStars) throws IOException, LoadException {
        if (this.JWTToken == null) throw new LoadException("User not logged in");
        String body = "{\"score\":\"" + score + "\",\"num_stars\":\"" + numStars + "\"}";
        Response res = this.__makeRequest(RequestMethod.Post, "/scores", body);
        if (res.getStatus() == ResponseStatus.Success) {
            //Succesfully added score, we should also update our local status now.
            this.__loadData();
            return null;
        }
        return res.loadJsonData();
    }

    /**
     * Request this users list of unlocked costumes from the api.
     * @return a hashset containing all of the users bought costumes
     * @throws Exception if unable to complete the request.
     */
    public HashSet<Avatar> getCostumes() throws Exception {
        this.__loadData();
        return this.unlockedAvatars;
    }

    /**
     * Get selected avatar
     * @return
     */
    public Avatar getSelectedAvatar(){
        return selectedAvatar;
    }

    public void setSelectedAvatar(Avatar avatar){
        this.selectedAvatar = avatar;
    }

    public String getNickname(){
        return nickname;
    }

    public String getUsername(){
        return username;
    }

    /**
     * Request the api to unlock a costume for this user
     * @param avatar the avatar the user wishes to attempt to unlock
     * @throws IOException throws if unable to complete the request
     * @throws LoadException if the user is not logged in
     */
    public String unlockCostume(Avatar avatar) throws IOException, LoadException {
        if (this.JWTToken == null) throw new LoadException("User not logged in");
        String body = "{\"score\":\"" + avatar.toString() + "\"}";
        Response res = this.__makeRequest(RequestMethod.Post, "/student/costumes", body);
        if (res.getStatus() == ResponseStatus.Success) {
            //Succesfully added score, we should also update our local status now.
            this.__loadData();
            return null;
        }
        return res.loadJsonData();
    }

    /**
     * Get the users achievements
     * @return a array list of the users achievements
     */
    public ArrayList<String> getProcuredAchievements(){
        return this.procuredAchievements;
    }

    /**
     * Check whether the user is logged in or not.
     * @return true if the user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return this.JWTToken != null;
    }

    /**
     * Get the number of games this user has played.
     * Note that if the user isn't logged in this will be null
     */
    public Integer getNumGamesPlayed() {
        return this.numGamesPlayed;
    }

    public Integer getNumStars(){
        return this.totalStars;
    }

    public Integer getNumAchievements(){
        return this.procuredAchievements.size();
    }

    public Integer getHighScore(){
        return this.highScore;
    }

    public void setHighScore(int score){
        this.highScore = score;
        //TODO: link to backend
    }

    public void changeStarCount(int delta){
        this.totalStars += delta;
        //TODO: link to backend
    }

    public void setUsername(String name){
        this.username = name;
        //TODO: link to backend
    }

    public void setNickname(String name){
        this.nickname = name;
        //TODO: link to backend
    }

    public boolean canPurchase(Avatar avatar){
        int cost = costAvatars.get(avatar);
        if (totalStars >= cost){
            return true;
        } else {
            return false;
        }
    }

    public void purchaseAvatar(Avatar avatar){
        if (hasBeenPurchased(avatar)) return;

        //TODO: link to backend
        unlockedAvatars.add(avatar);
        totalStars -= costAvatars.get(avatar);
    }

    public boolean hasBeenPurchased(Avatar avatar){
        return unlockedAvatars.contains(avatar);
    }

    public void addAchievement(Achievement achievement, int level){
        //TODO: link to backend
        procuredAchievements.add(achievement.toString(level));
    }
}
