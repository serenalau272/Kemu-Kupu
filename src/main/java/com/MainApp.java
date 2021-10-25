package com;

import java.io.IOException;

import com.components.animations.GlobalTimer;
import com.enums.ErrorModal;
import com.enums.Gamemode;
import com.enums.Views;
import com.models.Game;
import com.models.Setting;
import com.models.User;
import com.util.API;
import com.util.Modal;
import com.util.Sounds;
import com.util.TTS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage stage;
    private static Views view;
    private static StackPane stackPane;
    private static Game state;
    private static User user;
    private static Setting setting;
    private static TTS tts;
    private static GlobalTimer globalTimer;
    private static API api;

    //// Public Methods ////

    /**
     * Get the current gamestate
     * 
     * @return
     */
    public static Game getGameState() {
        return state;
    }

    /**
     * Get the current TTS
     * 
     * @return
     */
    public static TTS getTTS() {
        if (tts == null)
            tts = new TTS(); // Init TTS
        return tts;
    }

    /**
     * Get the current view
     * 
     * @return
     */
    public static Views getBaseView() {
        return view;
    }

    /**
     * Get the current active user, automatically initalises user with default value
     * if not exist.
     * 
     * @return a user
     */
    public static User getUser() {
        if (user == null){
            try {
                user = new User();
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            } 
            
            // Init with default user
            prepoluateUser2();
        }
            
        return user;
    }

    /**
     * Update the current user.
     * 
     * @param newUser the user to set
     */
    public static void setUser(User newUser) {
        user = newUser;
    }

    /**
     * Get the current settings. Will automatically initalise settings with default
     * values if not exist.
     * 
     * @return settings
     */
    public static Setting getSetting() {
        if (setting == null)
            setting = new Setting(); // Init default settings
        return setting;
    }

    /**
     * Update the settings for this application
     * 
     * @param newSetting
     */
    public static void setSetting(Setting newSetting) {
        setting = newSetting;
    }

    /**
     * Update the game state
     * 
     * @param newState
     */
    public static void setGameState(Game newState) {
        state = newState;
    }

    /**
     * Get the stackpane.
     * 
     * @return
     */
    public static StackPane getStackPane() {
        return stackPane;
    }

    /**
     * Set a new root application pane
     * 
     * @param view the view to load
     */
    public static void setRoot(Views view) {
        if (tts != null)
            tts.stopSpeech(); // Clear the queue

        try {
            MainApp.view = view;
            stackPane = new StackPane();
            stackPane.getChildren()
                    .add(new FXMLLoader(MainApp.class.getResource("/fxml/" + view.getFileName() + ".fxml")).load());
            Scene scene = new Scene(stackPane);
            if (view == Views.QUIZ) {
                scene.getStylesheets().add(MainApp.class.getResource("/styles/quiz.css").toExternalForm());
            } else {
                scene.getStylesheets().add(MainApp.class.getResource("/styles/application.css").toExternalForm());
            }
            stage.setTitle(view.getWindowName());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Unable to set root for fxml: " + view.getFileName());
            e.printStackTrace();
        }
    }

    /*
     * Update music on modal toggle
     */
    public static void updateMusic() {
        if (view == Views.QUIZ) {
            if (state.getGameMode() == Gamemode.RANKED) {
                Sounds.playMusic("game");
            } else {
                Sounds.playMusic("practice");
            }
        } else {
            Sounds.playMusic("menu");
        }
    }

    /**
     * Collect an instance of a globalTimer, used for the spinning wheel. Will
     * automatically initalise if needed.
     * 
     * @return a globalTimer instance
     */
    public static GlobalTimer getGlobalTimer() {
        if (globalTimer == null)
            globalTimer = new GlobalTimer(2 * 60);
        return globalTimer;
    }

    /**
     * Collect a instance of the api. Will automatically initalise if needed.
     * 
     * @return an api instance
     */
    public static API getAPI() {
        if (api == null)
            api = new API();
        return api;
    }

    @Override
    public void start(Stage s) {
        stage = s;
        stage.setResizable(false);
        setRoot(Views.MENU);
    }

    // populate user. to be deleted before submission
    private static void prepoluateUser(){
        try {
            User popUser = new User();

            //load user
            String res = popUser.login("User1", "123");
            if (res != null){
                //user does not already exist
                String s = popUser.signup("User1", "123", "Bob Jones");
                if (s != null){
                    System.err.println("Sign In Failed");
                }
            }

            //reset stats
            popUser.resetAccount();

            //add stars
            popUser.addScore(100, 500);

            //unlock achievements
            popUser.unlockAchievement("EXPLORER_1");
            popUser.unlockAchievement("EXPLORER_2");
            popUser.unlockAchievement("STUDENT_1");
            popUser.unlockAchievement("STUDENT_2");
            popUser.unlockAchievement("STUDENT_3");
            popUser.unlockAchievement("STUDENT_4");
            popUser.unlockAchievement("STUDENT_5");
            popUser.unlockAchievement("ACHIEVER_1");
            popUser.unlockAchievement("ACHIEVER_2");
            popUser.unlockAchievement("ACHIEVER_3");
            popUser.unlockAchievement("POCKETS_1");
            popUser.unlockAchievement("POCKETS_2");
            popUser.unlockAchievement("POCKETS_3");
            popUser.unlockAchievement("POCKETS_4");
            popUser.unlockAchievement("POCKETS_5");
            popUser.unlockAchievement("SPEEDY_1");

            setUser(popUser);
        } catch (IOException e){
            Modal.showGeneralModal(ErrorModal.INTERNET);
        }        
    }

    private static void prepoluateUser2(){
        try {

            User popUser = new User();
            //load user
            String res = popUser.login("User2", "123");
            if (res != null){
                //user does not already exist
                String s = popUser.signup("User2", "123", "Sandra Smith");
                if (s != null){
                    System.err.println("Sign In Failed");
                }
            }

            //reset stats
            popUser.resetAccount();

            //add stars
            popUser.addScore(95, 250);

            //unlock achievements
            popUser.unlockAchievement("EXPLORER_1");
            popUser.unlockAchievement("EXPLORER_2");
            popUser.unlockAchievement("STUDENT_1");
            popUser.unlockAchievement("STUDENT_2");
            popUser.unlockAchievement("STUDENT_3");
            popUser.unlockAchievement("STUDENT_4");
            popUser.unlockAchievement("STUDENT_5");
            popUser.unlockAchievement("ACHIEVER_1");
            popUser.unlockAchievement("ACHIEVER_2");
            popUser.unlockAchievement("POCKETS_1");
            popUser.unlockAchievement("POCKETS_2");
            popUser.unlockAchievement("POCKETS_3");
            popUser.unlockAchievement("POCKETS_4");
            popUser.unlockAchievement("SPEEDY_1");

            setUser(popUser);
        } catch (IOException e){
            Modal.showGeneralModal(ErrorModal.INTERNET);
        }
    }

    /**
     * A cleanup method for when we close the application.
     */
    @Override
    public void stop() {
        if (tts != null)
            tts.stopSpeech();
    }

    public static void main(String[] args) {
        launch(args);
    }
}