package com.models;

import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import java.util.HashSet;
import java.util.List;

import com.enums.Achievement;
import com.enums.Avatar;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

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
    public String name; //Internal name representation
    public String display_name; //shown to user
    public String description;
    public Integer price;
}

/**
 * One of many classes, exclusively used by gson to parse from a string into a java class for use internally.
 * This one is designed to be constructed to represent an achievement from json.
 */
class JsonAchievement {
    public String name; //Internal name representation
    public String display_name; //shown to user
    public String description;
}
/**
 * One of many classes, exclusively used by gson to parse from a string into a java class for use internally.
 * This one is designed to be constructed from a 200 response from GET /student endpoint.
 */
class JsonStudent {
    public Integer id;
    public String usr;
    public String current_costume;
    public String nickname;
    public List<JsonCostume> costumes;
    public List<JsonAchievement> achievements;
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
 * One of many classes, exclusively used by gson to parse from a string into a java class for use internally.
 * This one represents a collection of costumes from the api
 */
class JsonCostumes {
    public List<JsonCostume> data;
}

/**
 * This class handles the storing and retreiving of the current user.
 */
public class User implements Serializable {
    protected final String guestSavePath = "./.user/guest.data";
    protected final String userSavePath = "./.user/token.data";
    private final String apiPath = "https://kemukupu.com/api/v1";
    private String JWTToken;
    private String username;
    private String nickname;
    private Integer id;
    private Avatar selectedAvatar;
    private HashSet<Avatar> unlockedAvatars;
    private Integer highScore;
    private Integer totalStars;
    private Integer numGamesPlayed;
    private List<String> unlockedAchievements;
    private Map<Avatar, Integer> costAvatars = Map.ofEntries(
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.DEFAULT, 0),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.SAILOR, 5),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.MAGICIAN, 30),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.WIZARD, 5),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.NINJA, 100),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.QUEEN, 30),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.FAIRY, 80),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.PROFESSOR, 200),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.ALIEN, 50),
        new AbstractMap.SimpleEntry<Avatar, Integer>(Avatar.CHEF, 20)
    );

    /**
     * Save the internal state to the disk.
     * In the event of a logged in user, saves the jwt. For a guest user saves the raw data as a file.
     * @throws IOException in the event we are unable to save the disk for some reason.
     */
    private void __saveData() throws IOException {
        //Remove any existing save files
        new File(this.guestSavePath).delete();
        new File(this.userSavePath).delete();
        
        //Serialize new file
        if (this.JWTToken != null) {
            FileOutputStream f_out = new FileOutputStream(this.userSavePath);
            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
            obj_out.writeObject(this.JWTToken);
            obj_out.close();
        } else {
            FileOutputStream f_out = new FileOutputStream(this.guestSavePath);
            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
            obj_out.writeObject(this);
            obj_out.close();
        }
    }

    /**
     * Resets the entire state of the class to a fresh guest instance
     */
    private void __reset() {
        this.JWTToken = null;
        this.username = null;
        this.id = null;
        this.selectedAvatar = Avatar.DEFAULT;
        this.unlockedAvatars = new HashSet<>() { { add(Avatar.DEFAULT); } };
        this.highScore = 0;
        this.totalStars = 0;
        this.numGamesPlayed = 0;
        this.nickname = "";
        this.unlockedAchievements = new ArrayList<String>();
        try {
            this.__updatePrices();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.__saveData();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

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
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
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
     * Update the prices internally from the api
     * @throws IOException thrown when the service is unable to contact the server.
     */
    private void __updatePrices() throws IOException {
        Response res = this.__makeRequest(RequestMethod.Get, "/costume", null);
        JsonCostumes costumes = new Gson().fromJson(res.getBody(), JsonCostumes.class);
        //Load and process price data
        HashMap<Avatar, Integer> priceUpdate = new HashMap<>();
        for (JsonCostume avatarName : costumes.data) {
            Avatar newAvatar = Avatar.fromString(avatarName.name);
            if (!priceUpdate.containsKey(newAvatar))
                priceUpdate.put(newAvatar, avatarName.price);
        }
        this.costAvatars = priceUpdate;
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

        //Set nickname
        this.nickname = student.nickname;

         //Set username
        this.username = student.usr;

        //Set selected avatar
        this.selectedAvatar = Avatar.fromString(student.current_costume);
        
        //Set unlocked avatars
        HashSet<Avatar> avatarUpdate = new HashSet<>();
        for (JsonCostume avatarName : student.costumes) {
            Avatar newAvatar = Avatar.fromString(avatarName.name);
            if (!avatarUpdate.contains(newAvatar))
                avatarUpdate.add(newAvatar);
        }
        this.unlockedAvatars = avatarUpdate;

        //Load and Process Achievements
        List<String> achievementUpdate = new ArrayList<String>();
        for (JsonAchievement achievementName : student.achievements) {
            String newAchievement = Achievement.fromString(achievementName.name);
            if (!achievementUpdate.contains(newAchievement))
                achievementUpdate.add(newAchievement);
        }
        this.unlockedAchievements = achievementUpdate;

        //Load and process score data
        res = this.__makeRequest(RequestMethod.Get, "/scores?id="+ this.id, null);
        JsonScores scores = new Gson().fromJson(res.getBody(), JsonScores.class);

        int newGamesPlayed = 0;
        int newHighScore = 0;
        int newTotalStars = 0;
        for (JsonScore score : scores.data) {
            newTotalStars += score.num_stars;
            if (score.score > newHighScore)
                newHighScore = score.score;
            //HACK to allow additional stars to be added.
            if (score.score != -1)
                newGamesPlayed++;
        }
        this.totalStars = newTotalStars;
        this.highScore = newHighScore;        
        this.numGamesPlayed = newGamesPlayed;
        this.__saveData();
    }

    public User() {
        if (new File(this.userSavePath).isFile()) {
            try {
                FileInputStream f_in = new FileInputStream(this.userSavePath);
                ObjectInputStream obj_in = new ObjectInputStream(f_in);
                Object obj = obj_in.readObject();
                obj_in.close();
                if (obj instanceof String) {
                    String str = (String) obj;
                    this.JWTToken = str;
                    this.__loadData();
                    return;
                }
            } catch (IOException | ClassNotFoundException e) { 
                e.printStackTrace();
            }
        } else if (new File(this.guestSavePath).isFile()) {
            try {
                FileInputStream f_in = new FileInputStream(this.guestSavePath);
                ObjectInputStream obj_in = new ObjectInputStream(f_in);
                Object obj = obj_in.readObject();
                obj_in.close();
                if (obj instanceof User) {
                    User user = (User) obj;
                    //Load relevant data from serialized object
                    this.JWTToken = user.JWTToken;
                    this.username = user.username;
                    this.nickname = user.nickname;
                    this.id = user.id;
                    this.selectedAvatar = user.selectedAvatar;
                    this.unlockedAvatars = user.unlockedAvatars;
                    this.highScore = user.highScore;
                    this.totalStars = user.totalStars;
                    this.numGamesPlayed = user.numGamesPlayed;
                    this.unlockedAchievements = user.unlockedAchievements;
                    this.costAvatars = user.costAvatars;
                    return;
                }
            } catch (IOException | ClassNotFoundException e) { 
                e.printStackTrace();
            }
        }
        this.__reset();
    }

    /**
     * Create a new user with the api, and log them in automatically.
     * @param username the username
     * @param password the password to signup with
     * @param nickname the users nickname
     * @throws IOException if unable to complete the request
     */
    public String signup(String name, String password, String nickname) throws IOException {
        String body = "{\"usr\":\"" + name.replace("\"", "\\\"") + "\",\"pwd\":\"" + password.replace("\"", "\\\"") + "\",\"nickname\":\"" + nickname.replace("\"", "\\\"") + "\"}";
        Response res = this.__makeRequest(RequestMethod.Post, "/student/create", body);
        if (res.getStatus() == ResponseStatus.Success) {
            //Succesful login, lets go from here.
            this.JWTToken = res.loadJsonData();
            this.username = name;
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
    public String login(String name, String password) throws IOException {
        String body = "{\"usr\":\"" + name.replace("\"", "\\\"") + "\",\"pwd\":\"" + password.replace("\"", "\\\"") + "\"}";
        Response res = this.__makeRequest(RequestMethod.Post, "/student/login", body);
        if (res.getStatus() == ResponseStatus.Success) {
            //Succesful login, lets go from here. main
            this.JWTToken = res.loadJsonData();
            this.username = name;
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
     */
    public String addScore(int score, int numStars) throws IOException {
        if (this.JWTToken != null) {
            //User logged in
            String body = "{\"score\":" + score + ",\"num_stars\":" + numStars + "}";
            Response res = this.__makeRequest(RequestMethod.Post, "/scores", body);
            if (res.getStatus() == ResponseStatus.Success) {
                //Succesfully added score, we should also update our local status now.
                this.__loadData();
                return null;
            }
            return res.loadJsonData();
        } else {
            //If user not logged in
            if (score > this.highScore)
                this.highScore = score;
            this.numGamesPlayed +=1 ;
            this.totalStars += numStars;
            this.__saveData();
            return null;
        }
    }

    /**
     * Request the api to unlock a costume for this user
     * @param avatar the avatar the user wishes to attempt to unlock
     * @throws IOException throws if unable to complete the request
     */
    public String unlockCostume(Avatar avatar) throws IOException {
        if (this.unlockedAvatars.contains(avatar)) return null;

        if (this.JWTToken != null) {
            String body = "{\"name\":\"" + avatar.toString() + "\"}";
            Response res = this.__makeRequest(RequestMethod.Post, "/student/costumes", body);
            if (res.getStatus() == ResponseStatus.Success) {
                //Succesfully added score, we should also update our local status now.
                String addScoreRes = this.addScore(-1, -this.getPrice(avatar));
                this.__loadData();

                //check to see if stylish badge can be awarded
                if (this.unlockedAvatars.size() == 10){
                    this.unlockAchievement("STYLISH_1");
                }

                return addScoreRes;
            }
            return res.loadJsonData();
        } else {
            //Guest account = note that no check is performed
            this.unlockedAvatars.add(avatar);
            this.totalStars -= this.getPrice(avatar);
            this.__saveData();
            return null;
        }

    }

    /**
     * Request the api to unlock an achievement for this user.
     * WARNING: THE API DOES NO VALIDATION ON IF THEY HAVE ACTUALLY EARNED THIS, THAT NEEDS TO BE DONE JAVA-SIDE FOR NOW.
     * @param achievement the achievement to unlock
     * @throws IOException throws if unable to complete the request
     */
    public String unlockAchievement(String achievement) throws IOException {
        if (this.unlockedAchievements.contains(achievement)) return null;

        if (this.JWTToken != null) {
            String body = "{\"name\":\"" + Achievement.toString(achievement) + "\"}";
            Response res = this.__makeRequest(RequestMethod.Post, "/student/achievement", body);
            if (res.getStatus() == ResponseStatus.Success) {
                //Succesfully added score, we should also update our local status now.
                this.__loadData();
                return null;
            }
            return res.loadJsonData();    
        } else {
            //Guest account
            this.unlockedAchievements.add(achievement);
            this.__saveData();
            return null;
        }
    }

    /**
     * Change this users avatar
     * @param avatar the avatar to change to 
     * @return a string, null if success or with a failure message if not.
     * @throws IOException if unable to contact api
     */
    public String setAvatar(Avatar avatar) throws IOException {
        if (!this.unlockedAvatars.contains(avatar))
            return "Avatar not unlocked!";
        if (this.JWTToken != null) {
            Response res = this.__makeRequest(RequestMethod.Post, "/student/" + avatar.toString().replace("\"", "\\\""), "");
            if (res.getStatus() == ResponseStatus.Success) {
                this.__loadData();
                return null;
            }
            return res.loadJsonData();
            
        } else {
            this.selectedAvatar = avatar;
            this.__saveData();
            return null;
        }
    }

    /**
     * Change this users username
     * @param name the username to change to
     * @return a string, null if success or with a failure message if not.
     * @throws IOException if unable to contact api
     */
    public String setUsername(String name) throws IOException {
        if (this.JWTToken != null) {
            String body = "{\"name\":\"" + name.toString().replace("\"", "\\\"") + "\"}";
            Response res = this.__makeRequest(RequestMethod.Post, "/student/username", body);
            if (res.getStatus() == ResponseStatus.Success) {
                this.__loadData();
                return null;
            }
            return res.loadJsonData();
        } else {
            this.username = name;
            this.__saveData();
            return null;
        }
    }

    /**
     * Change this users nickname
     * @param name the nickname to change to
     * @return a string, null if success or with a failure message if not.
     * @throws IOException if unable to contact api
     */
    public String setNickname(String name) throws IOException {
        if (this.JWTToken != null) {
            String body = "{\"name\":\"" + name.toString().replace("\"", "\\\"") + "\"}";
            Response res = this.__makeRequest(RequestMethod.Post, "/student/nickname", body);
            if (res.getStatus() == ResponseStatus.Success) {
                this.__loadData();
                return null;
            }
            return res.loadJsonData();
        } else {
            this.nickname = name;
            this.__saveData();
            return null;
        }
    }

    /**
     * Delete this user account, this is a permenant irreversible action.
     * This method will still reset the guest account.
     * @returns a string which is null if succesful, and contains an error message otherwise.
     * @throws IOException throws if unable to complete the request
     */
    public String deleteAccount() throws IOException {
        //Delete save files
        new File(this.guestSavePath).delete();
        new File(this.userSavePath).delete();
        //If logged in, send deletion request
        if (this.JWTToken != null) {
            Response res = this.__makeRequest(RequestMethod.Delete, "/student", null);
            if (res.getStatus() == ResponseStatus.Failure)
                return res.loadJsonData();
        }
        //Reset this instance
        this.__reset();
        return null;
    }

    /**
     * Reset this user account data, this is a permenant irreversible action.
     * @returns a string which is null if succesful, and contains an error message otherwise.
     * @throws IOException
     */
    public String resetAccount() throws IOException {
        if (this.JWTToken != null) {
            Response res = this.__makeRequest(RequestMethod.Post, "/student/reset", "");
            if (res.getStatus() == ResponseStatus.Success) {
                this.__loadData();
                return null;
            }
            return res.loadJsonData();
        }
        this.__reset();
        return null;
    }

    /**
     * Check whether the user is logged in or not.
     * @return true if the user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return this.JWTToken != null;
    }

    /**
     * Collects the price of an avatar from the api.
     * Note that if the user hasn't logged in, this will be the default values.
     */
    public Integer getPrice(Avatar avatar) {
        try {
            this.__updatePrices();
        } catch (Exception e) { /* Burn Failures */ }
        return this.costAvatars.get(avatar);
    }
    
    /**
     * Get the users achievements
     * @return a hashset of the users achievements
     */
    public List<String> getAchievements(){
        return this.unlockedAchievements;
    }

    /**
     * Request this users list of unlocked costumes from the api.
     * @return a hashset containing all of the users bought costumes
     */
    public HashSet<Avatar> getCostumes() {
        if (this.JWTToken != null) {
            try {
                this.__loadData();
            } catch (IOException e) {}   
        }
        return this.unlockedAvatars;
    }

    public Avatar getSelectedAvatar() {
        return this.selectedAvatar;
    }

    public Integer getNumGamesPlayed() {
        return this.numGamesPlayed;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getUsername() {
        return this.username;
    }

    public Integer getHighScore() {
        return this.highScore;
    }

    public Integer getTotalStars() {
        return this.totalStars;
    }

    public boolean canPurchase(Avatar avatar) {
        int cost = costAvatars.get(avatar);
        return totalStars >= cost;
    }

    public boolean hasBeenPurchased(Avatar avatar) {
        return unlockedAvatars.contains(avatar);
    }

    public Integer getNumAchievements() {
        return this.unlockedAchievements.size();
    }

    public String changeStars(int delta) throws IOException {
        this.addScore(-1, delta);
        return null;
    }
}
