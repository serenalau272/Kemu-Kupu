package com;
import java.io.File;
import java.io.IOException;

import com.components.animations.GlobalTimer;
import com.enums.Gamemode;
import com.enums.Views;
import com.models.Game;
import com.models.Setting;
import com.util.Sounds;
import com.util.TTS;
import com.util.User;

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

    //// Private (helper) methods ////
    /**
     * set a new root application pane
     * @param fxml the name of the fxml file to load
     * @param title the title of the window to be spawned
     * @return a stage which can be set or spawned as a modal
     */
    private static void __setRoot(Views view) {
        tts.stopSpeech(); //Clear the queue
        try {
            MainApp.view = view;
            stackPane = new StackPane();
            stackPane.getChildren().add(new FXMLLoader(MainApp.class.getResource("/fxml/" + view.getFileName() + ".fxml")).load());        
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

    private void configureStatsFiles() throws IOException {
        File file = new File("./.user/.userStats.txt");
        file.createNewFile();
    }

    //// Public Methods ////

    /**
     * Get the current gamestate
     * @return
     */
    public static Game getGameState() {
        return state;
    }

    /**
     * Get the current TTS
     * @return
     */
    public static TTS getTTS(){
        return tts;
    }

    /**
     * Get the current view
     * @return
     */
    public static Views getBaseView() {
        return view;
    }

    /**
     * Get user
     * @return
     */
    public static User getUser() {
        return user;
    }

    /**
     * Update the game state
     * @param newState
     */
    public static void setGameState(Game newState) {
        state = newState;
    }

    /**
     * Get the current settings.
     * @return
     */
    public static Setting getSetting() {
        return setting;
    }

    /**
     * Get the stackpane.
     * @return
     */
    public static StackPane getStackPane() {
        return stackPane;
    }

    /**
     * Update the settings for this application
     * @param newSetting
     */
    public static void setSetting(Setting newSetting) {
        setting = newSetting;
    }

    /**
     * Change which scene the user is looking at.
     * @param view to load
     */
    public static void setRoot(Views view) {
        __setRoot(view);
    }
    

    /*
    * update music on modal toggle
    */
    public static void updateMusic(){
        if (view == Views.QUIZ) {
            if (state.getGameMode() == Gamemode.RANKED){
                Sounds.playMusic("game");
            } else {
                Sounds.playMusic("practice");
            }
        } else {
            Sounds.playMusic("menu");
        }
    }

    public static void setUser(){
        user = new User();
    }

    public static void setUser(User usr){
        user = usr;
    }

    private static void prepoluateUser(){
        User popUser = new User();

        try {
             //load user
            String res = popUser.login("team11GOATS", "123");
            if (res != null){
                //user does not already exist
                String s = popUser.signup("team11GOATS", "123", "Team 11");
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
        } catch (IOException e){
            System.err.println("Unable to make request/s");
        }

        setUser(popUser);
    }

    public static GlobalTimer getGlobalTimer(){
        return globalTimer;
    }

    @Override
    public void start(Stage s) {
        globalTimer = new GlobalTimer(2 * 60);
        // globalTimer.restart();

        stage = s;
        setting = new Setting();
        stage.setResizable(false);
        tts = new TTS();

        setUser();

        prepoluateUser();   //uncomment when needing to populate a user

        try {
            configureStatsFiles();
        } catch (IOException e) {
            System.err.println("Unable to create user stats file");
            e.printStackTrace();
        }
        setRoot(Views.MENU);
    }

    /**
     * A cleanup method for when we close the application.
     */
    @Override
    public void stop() {
        tts.stopSpeech();
    }

    public static void main(String[] args) {
        launch(args);
    }
}