package com.se206.g11;
import java.io.File;
import java.io.IOException;

import com.se206.g11.components.InputField;
import com.se206.g11.components.ResultsList;
import com.se206.g11.enums.Gamemode;
import com.se206.g11.enums.Modals;
import com.se206.g11.enums.View;
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
    private static View view;
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

            if (view == View.RESULTS){
                ResultsList.configureEntries();
            }
            
        } catch (IOException e) {
            System.err.println("Unable to set root for fxml: " + view.getFileName());
            e.printStackTrace();
        }
    }

    /**
     * Disable all screen nodes
     */
    public static void disableScreenNodes(boolean isDisable) {
        stackPane.getChildren().get(0).setDisable(isDisable);
        for (int i=0; i<stackPane.getChildren().size()-2; i++) {
            stackPane.getChildren().get(i).setDisable(isDisable);
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
    
    /**
     * Load and show a modal to the user
     * @param m the modal to load
     */
    public static void showModal(Modals m) {
        try {
            //Duplicate code should be refactored at some point
            if (view == View.QUIZ) state.getClock().stop();
            disableScreenNodes(true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/" + m.getFileName() + ".fxml"));
            Node modal = (Node) fxmlLoader.load();
            stackPane.getChildren().add(modal);
            addBlur();
        } catch (Exception e) {
            System.err.println("Unable to load modal " + m.getFileName() + " due to error " + e.toString()); 
            return;
        }
    }

    /**
     * closes modal
     */
    public static void closeModal() {
        Sounds.playSoundEffect("pop");
        int size = stackPane.getChildren().size();
        stackPane.getChildren().remove(size-1);
        removeBlur();
        disableScreenNodes(false);
        if (view == View.QUIZ) {
            state.getClock().resume();
            InputField.recursor();
        }
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
        try {
            configureStatsFiles();
        } catch (IOException e) {
            System.err.println("Unable to create user stats file");
            e.printStackTrace();
        }
        setRoot(View.MENU);
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