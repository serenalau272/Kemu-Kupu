package com.se206.g11;
import java.io.IOException;

import com.se206.g11.models.Game;
import com.se206.g11.models.Setting;
import com.se206.g11.util.Sounds;
import com.se206.g11.util.TTS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.effect.BoxBlur;

public class MainApp extends Application {
    private static Stage stage;
    private static StackPane stackPane;
    private static Game state;
    private static Setting setting;
    public static TTS tts;

    //// Private (helper) methods ////
    /**
     * set a new root application pane
     * @param fxml the name of the fxml file to load
     * @param title the title of the window to be spawned
     * @return a stage which can be set or spawned as a modal
     */
    private static void __setRoot(String fxml, String title) {
        tts.stopSpeech(); //Clear the queue
        try {
            stackPane = new StackPane();
            stackPane.getChildren().add(new FXMLLoader(MainApp.class.getResource("/fxml/" + fxml + ".fxml")).load());
            Scene scene = new Scene(stackPane);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();              
        } catch (IOException e) {
            System.err.println("Unable to set root for fxml: " + fxml);
            e.printStackTrace();
        }
    }

    /**
     * Disable all screen nodes
     */
    private static void disableScreenNodes(boolean isDisable) {
        stackPane.getChildren().get(0).setDisable(isDisable);
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
     * Update the settings for this application
     * @param newSetting
     */
    public static void setSetting(Setting newSetting) {
        setting = newSetting;
    }

    /**
     * Change which scene the user is looking at.
     * @param fxml the name of the scene to load
     * @param title the title of the window to set
     */
    public static void setRoot(String fxml, String title) {
        __setRoot(fxml, title);
    }
    
    /**
     * Load and show a modal to the user
     * @param fxml the name of the fxml to load in a modal
     * @param title the title of the modal window
     */
    //TODO avoid duplicate code here, merge functionality with __setRoot. This should improve readability & extendability of code. For MVP is fine though.
    public static void showModal(String fxml, String title) {
        try {
            //Duplicate code should be refactored at some point
            disableScreenNodes(true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/" + fxml + ".fxml"));
            Node modal = (Node) fxmlLoader.load();
            stackPane.getChildren().add(modal);
            addBlur();
        } catch (Exception e) {
            System.err.println("Unable to load modal " + fxml + " due to error " + e.toString()); 
            return;
        }
    }

    /**
     * closes modal
     */
    public static void closeModal() {
        Sounds.playSoundEffect("pop");
        stackPane.getChildren().remove(1);
        removeBlur();
        disableScreenNodes(false);

    }

    /**
     * adds blur from background pane
     */
    public static void addBlur() {
        BoxBlur blur = new BoxBlur();
        blur.setIterations(2);
        stackPane.getChildren().get(0).setEffect(blur);
    }

    /**
     * removes blur from background pane
     */
    public static void removeBlur() {
        stackPane.getChildren().get(0).setEffect(null);
    }

    @Override
    public void start(Stage s) {
        stage = s;
        setting = new Setting();
        stage.setResizable(false);
        tts = new TTS();
        setRoot("Menu","Kemu Kupu");
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