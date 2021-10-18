package com;
import java.io.File;
import java.io.IOException;

import com.enums.Gamemode;
import com.enums.View;
import com.models.Game;
import com.models.Setting;
import com.models.User;
import com.util.Sounds;
import com.util.TTS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage stage;
    private static View view;
    private static StackPane stackPane;
    private static Game state;
    private static User user;
    private static Setting setting;
    private static TTS tts;
    private static long lastSpun;

    //// Private (helper) methods ////
    /**
     * set a new root application pane
     * @param fxml the name of the fxml file to load
     * @param title the title of the window to be spawned
     * @return a stage which can be set or spawned as a modal
     */
    private static void __setRoot(View view) {
        tts.stopSpeech(); //Clear the queue
        try {
            MainApp.view = view;
            stackPane = new StackPane();
            stackPane.getChildren().add(new FXMLLoader(MainApp.class.getResource("/fxml/" + view.getFileName() + ".fxml")).load());        
            Scene scene = new Scene(stackPane);
            scene.getStylesheets().add(MainApp.class.getResource("/styles/application.css").toExternalForm());
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
    public static View getBaseView() {
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
    public static void setRoot(View view) {
        __setRoot(view);
    }
    

    /*
    * update music on modal toggle
    */
    public static void updateMusic(){
        if (view == View.QUIZ) {
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

    @Override
    public void start(Stage s) {
        lastSpun = System.currentTimeMillis() - 150_000;   //2 minutes = 60 * 2 * 1000 = 120 000
        stage = s;
        setting = new Setting();
        stage.setResizable(false);
        tts = new TTS();
        user = new User();
        try {
            configureStatsFiles();
        } catch (IOException e) {
            System.err.println("Unable to create user stats file");
            e.printStackTrace();
        }
        setRoot(View.MENU);
    }

    public static boolean canSpin(){
        long timeNow = System.currentTimeMillis();
        if (timeNow - lastSpun >= 120_000){
            //spin
            lastSpun = timeNow;
            return true;
        } else {
            return false;
        }
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