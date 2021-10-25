package com.models;

import java.util.HashSet;
import java.util.List;

import com.MainApp;
import com.enums.Achievement;
import com.enums.Avatar;
import com.enums.ErrorModal;
import com.google.gson.Gson;
import com.models.APIModels.JSONAchievement;
import com.models.APIModels.JSONCostume;
import com.models.APIModels.JSONScore;
import com.models.APIModels.JSONScores;
import com.models.APIModels.JSONStudent;
import com.models.APIModels.RequestMethod;
import com.models.APIModels.Response;
import com.models.APIModels.ResponseStatus;
import com.util.API;
import com.util.Modal;
import com.util.Sounds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class handles the storing and retreiving of the current user. It has
 * interactions with the api for saving and retriving data. It also serializes
 * data onto the disk, which allows persistent logins and guest accounts to
 * function correctly.
 */
public class User implements Serializable {
    protected final String guestSavePath = "./.user/guest.data";
    protected final String userSavePath = "./.user/token.data";

    //// Properties ////
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

    //// Private (Helper) Methods ////

    /**
     * Save the internal state to the disk. In the event of a logged in user, saves
     * the jwt. For a guest user saves the raw data as a file.
     * 
     * @throws IOException in the event we are unable to save the disk for some
     *                     reason.
     */
    private void __saveData() throws IOException {
        // Remove any existing save files
        new File(this.guestSavePath).delete();
        new File(this.userSavePath).delete();

        // Serialize new file
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
        this.unlockedAvatars = new HashSet<>() {
            {
                add(Avatar.DEFAULT);
            }
        };
        this.highScore = 0;
        this.totalStars = 0;
        this.numGamesPlayed = 0;
        this.nickname = "";
        this.unlockedAchievements = new ArrayList<String>();
        try {
            this.__saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * After user has logged in, or been created, this method loads their data from
     * the API.
     * 
     * @throws IOException if unable to contact the api
     */
    private void __loadData() throws IOException {
        // Load response and parse json
        Response res = API.makeRequest(RequestMethod.Get, "/student", null, this.JWTToken);
        JSONStudent student = new Gson().fromJson("{" + res.loadJsonData() + "}", JSONStudent.class);

        // Set id
        this.id = student.id;

        // Set nickname
        this.nickname = student.nickname;

        // Set username
        this.username = student.usr;

        // Set selected avatar
        this.selectedAvatar = Avatar.fromString(student.current_costume);

        // Set unlocked avatars
        HashSet<Avatar> avatarUpdate = new HashSet<>();
        for (JSONCostume avatarName : student.costumes) {
            Avatar newAvatar = Avatar.fromString(avatarName.name);
            if (!avatarUpdate.contains(newAvatar))
                avatarUpdate.add(newAvatar);
        }
        this.unlockedAvatars = avatarUpdate;

        // Load and Process Achievements
        List<String> achievementUpdate = new ArrayList<String>();
        for (JSONAchievement achievementName : student.achievements) {
            String newAchievement = Achievement.fromString(achievementName.name);
            if (!achievementUpdate.contains(newAchievement))
                achievementUpdate.add(newAchievement);
        }
        this.unlockedAchievements = achievementUpdate;

        // Load and process score data
        res = API.makeRequest(RequestMethod.Get, "/scores?id=" + this.id, null, this.JWTToken);
        JSONScores scores = new Gson().fromJson(res.getBody(), JSONScores.class);

        int newGamesPlayed = 0;
        int newHighScore = 0;
        int newTotalStars = 0;
        for (JSONScore score : scores.data) {
            newTotalStars += score.num_stars;
            if (score.score > newHighScore)
                newHighScore = score.score;
            // HACK to allow additional stars to be added.
            if (score.score != -1)
                newGamesPlayed++;
        }
        this.totalStars = newTotalStars;
        this.highScore = newHighScore;
        this.numGamesPlayed = newGamesPlayed;
        this.__saveData();
    }

    //// Public Methods ////

    public User() {
        if (new File(this.userSavePath).isFile()) {
            //Load from api
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
            //Load from disk
            try {
                FileInputStream f_in = new FileInputStream(this.guestSavePath);
                ObjectInputStream obj_in = new ObjectInputStream(f_in);
                Object obj = obj_in.readObject();
                obj_in.close();
                if (obj instanceof User) {
                    User user = (User) obj;
                    // Load relevant data from serialized object
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
     * 
     * @param username the username
     * @param password the password to signup with
     * @param nickname the users nickname
     * @throws IOException if unable to complete the request
     */
    public String signup(String name, String password, String nickname) throws IOException {
        String body = "{\"usr\":\"" + name.replace("\"", "\\\"") + "\",\"pwd\":\"" + password.replace("\"", "\\\"")
                + "\",\"nickname\":\"" + nickname.replace("\"", "\\\"") + "\"}";
        Response res = API.makeRequest(RequestMethod.Post, "/student/create", body, this.JWTToken);
        if (res.getStatus() == ResponseStatus.Success) {
            // Succesful login, lets go from here.
            this.JWTToken = res.loadJsonData();
            this.username = name;
            // Load user data
            this.__loadData();
            return null;
        }
        return res.loadJsonData();
    }

    /**
     * Login a user to the api
     * 
     * @param username the username of the account
     * @param password the password of the account
     * @throws IOException if unable to complete the request
     */
    public String login(String name, String password) throws IOException {
        String body = "{\"usr\":\"" + name.replace("\"", "\\\"") + "\",\"pwd\":\"" + password.replace("\"", "\\\"")
                + "\"}";
        Response res = API.makeRequest(RequestMethod.Post, "/student/login", body, this.JWTToken);
        if (res.getStatus() == ResponseStatus.Success) {
            // Succesful login, lets go from here. main
            this.JWTToken = res.loadJsonData();
            this.username = name;
            // Load user data
            this.__loadData();
            return null;
        }
        return res.loadJsonData();
    }

    /**
     * Save a new score for this user, should be called at the end of the quiz.
     * 
     * @param score the score to be saved
     * @return null if success, string with error to be displayed to user otherwise
     * @throws IOException throws if unable to complete the request
     */
    public String addScore(int score, int numStars) throws IOException {
        if (this.JWTToken != null) {
            // User logged in
            String body = "{\"score\":" + score + ",\"num_stars\":" + numStars + "}";
            Response res = API.makeRequest(RequestMethod.Post, "/scores", body, this.JWTToken);
            if (res.getStatus() == ResponseStatus.Success) {
                // Succesfully added score, we should also update our local status now.
                this.__loadData();
                return null;
            }
            return res.loadJsonData();
        } else {
            // If user not logged in
            if (score > this.highScore)
                this.highScore = score;
            this.numGamesPlayed += 1;
            this.totalStars += numStars;
            this.__saveData();
            return null;
        }
    }

    /**
     * Request the api to unlock a costume for this user
     * 
     * @param avatar the avatar the user wishes to attempt to unlock
     * @return a string, null if succesful, or containing an error message otherwise
     * @throws IOException throws if unable to complete the request
     */
    public String unlockCostume(Avatar avatar) throws IOException {
        // Validate purchase
        if (this.unlockedAvatars.contains(avatar))
            return "Avatar already unlocked";
        if (!this.canPurchase(avatar))
            return "Unable to afford avatar";

        if (MainApp.getSetting() != null)
            Sounds.playSoundEffect("cha-ching");

        // Carry out purchase
        if (this.JWTToken != null) {
            String body = "{\"name\":\"" + avatar.toString() + "\"}";
            Response res = API.makeRequest(RequestMethod.Post, "/student/costumes", body, this.JWTToken);
            if (res.getStatus() == ResponseStatus.Success) {
                // Succesfully added score, we should also update our local status now.
                String addScoreRes = this.addScore(-1, -MainApp.getAPI().getPrice(avatar));
                this.__loadData();

                // check to see if stylish badge can be awarded
                if (this.unlockedAvatars.size() == 10) {
                    this.unlockAchievement("STYLISH_1");
                }

                return addScoreRes;
            }
            return res.loadJsonData();
        } else {
            // Guest account
            this.unlockedAvatars.add(avatar);
            this.totalStars -= MainApp.getAPI().getPrice(avatar);
            this.__saveData();
            return null;
        }
    }

    /**
     * Request the api to unlock an achievement for this user. WARNING: THE API DOES
     * THE API DOES NOT VALIDATE IF THEY HAVE ACTUALLY EARNED THIS, THAT NEEDS TO BE DONE
     * JAVA-SIDE FOR NOW.
     * 
     * @param achievement the achievement to unlock
     * @throws IOException throws if unable to complete the request
     */
    public String unlockAchievement(String achievement) throws IOException {
        if (this.unlockedAchievements.contains(achievement))
            return null;

        if (this.JWTToken != null) {
            String body = "{\"name\":\"" + Achievement.toString(achievement) + "\"}";
            Response res = API.makeRequest(RequestMethod.Post, "/student/achievement", body, this.JWTToken);
            if (res.getStatus() == ResponseStatus.Success) {
                // Succesfully added score, we should also update our local status now.
                this.__loadData();
                return null;
            }
            return res.loadJsonData();
        } else {
            // Guest account
            this.unlockedAchievements.add(achievement);
            this.__saveData();
            return null;
        }
    }

    /**
     * Change this users avatar
     * 
     * @param avatar the avatar to change to
     * @return a string, null if success or with a failure message if not.
     * @throws IOException if unable to contact api
     */
    public String setAvatar(Avatar avatar) throws IOException {
        if (!this.unlockedAvatars.contains(avatar))
            return "Avatar not unlocked!";
        if (this.JWTToken != null) {
            Response res = API.makeRequest(RequestMethod.Post, "/student/" + avatar.toString().replace("\"", "\\\""),
                    "", this.JWTToken);
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
     * 
     * @param name the username to change to
     * @return a string, null if success or with a failure message if not.
     * @throws IOException if unable to contact api
     */
    public String setUsername(String name) throws IOException {
        if (this.JWTToken != null) {
            String body = "{\"name\":\"" + name.toString().replace("\"", "\\\"") + "\"}";
            Response res = API.makeRequest(RequestMethod.Post, "/student/username", body, this.JWTToken);
            if (res.getStatus() == ResponseStatus.Success) {
                this.__loadData();
                return null;
            }
            Modal.showGeneralModal(ErrorModal.USERNAME);
            return res.loadJsonData();
        } else {
            this.username = name;
            this.__saveData();
            return null;
        }
    }

    /**
     * Change this users nickname
     * 
     * @param name the nickname to change to
     * @return a string, null if success or with a failure message if not.
     * @throws IOException if unable to contact api
     */
    public String setNickname(String name) throws IOException {
        if (this.JWTToken != null) {
            String body = "{\"name\":\"" + name.toString().replace("\"", "\\\"") + "\"}";
            Response res = API.makeRequest(RequestMethod.Post, "/student/nickname", body, this.JWTToken);
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
     * Sign the user out, note that if the user is not logged in will merely reset
     * their account.
     */
    public void signout() {
        if (this.JWTToken == null)
            System.err.println("Warning! Logout was clicked with a guest account.");
        // Reset Account
        this.__reset();
    }

    /**
     * Delete this user account, this is a permenant irreversible action. This
     * method will still reset the guest account.
     * 
     * @returns a string which is null if succesful, and contains an error message
     *          otherwise.
     * @throws IOException throws if unable to complete the request
     */
    public String deleteAccount() throws IOException {
        // If logged in, send deletion request
        if (this.JWTToken != null) {
            Response res = API.makeRequest(RequestMethod.Delete, "/student", null, this.JWTToken);
            if (res.getStatus() == ResponseStatus.Failure)
                return res.loadJsonData();
        }
        // Reset this instance
        this.__reset();
        return null;
    }

    /**
     * Reset this user account data, this is a permenant irreversible action.
     * 
     * @returns a string which is null if succesful, and contains an error message
     *          otherwise.
     * @throws IOException
     */
    public String resetAccount() throws IOException {
        if (this.JWTToken != null) {
            Response res = API.makeRequest(RequestMethod.Post, "/student/reset", "", this.JWTToken);
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
     * 
     * @return true if the user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return this.JWTToken != null;
    }

    /**
     * Get the users achievements
     * 
     * @return a hashset of the users achievements
     */
    public List<String> getAchievements() {
        return this.unlockedAchievements;
    }

    /**
     * Request this users list of unlocked costumes from the API.
     * 
     * @return a hashset containing all of the users bought costumes
     */
    public HashSet<Avatar> getCostumes() {
        if (this.JWTToken != null) {
            try {
                this.__loadData();
            } catch (IOException e) {
            }
        }
        return this.unlockedAvatars;
    }

    /**
     * Checks whether a user has enough money to make a purchase of a costume.
     * 
     * @param avatar the avatar the user wishes to purchase
     * @return a boolean, which is true if the avatar can be afforded and false
     *         otherwise.
     */
    public boolean canPurchase(Avatar avatar) {
        int cost = MainApp.getAPI().getPrice(avatar);
        return totalStars >= cost;
    }

    /**
     * Checks whether an avatar has already been purchased by this user.
     * 
     * @param avatar the avatar to investigate
     * @return a boolean, true if the user has purchased this avatar, and false
     *         otherwise.
     */
    public boolean hasBeenPurchased(Avatar avatar) {
        return unlockedAvatars.contains(avatar);
    }

    /**
     * Change this users number of stars by a custom among, either positive or
     * negative.
     * 
     * @param delta the amount to change the stars by
     * @return a string, which is null if everything was succesful and contains an
     *         error message otherwise.
     * @throws IOException if unable to contact the server.
     */
    public String changeStars(int delta) throws IOException {
        this.addScore(-1, delta);
        return null;
    }

    /**
     * Get the users currently selected avatar
     * @return an avatar enum representing the users avatar
     */
    public Avatar getSelectedAvatar() {
        return this.selectedAvatar;
    }

    /**
     * Get the number of games this user has played.
     * @return the number of games played
     */
    public Integer getNumGamesPlayed() {
        return this.numGamesPlayed;
    }

    /**
     * Get this users nickname
     * @return a string representing the current users nickname. Empty if guest.
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Get this users username
     * @return a string representing the current users username. Empty if guest.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get this users high score.
     * @return an integer representing highest score
     */
    public Integer getHighScore() {
        return this.highScore;
    }

    /**
     * Get the total number of stars this user has earned.
     * @return an integer with their total stars
     */
    public Integer getTotalStars() {
        return this.totalStars;
    }

    /**
     * Get the number of achievements this user has unlocked.
     * @return the size of their unlocked achievements array.
     */
    public Integer getNumAchievements() {
        return this.unlockedAchievements.size();
    }
}
