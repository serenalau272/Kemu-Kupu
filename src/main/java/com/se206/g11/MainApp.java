package com.se206.g11;
import java.util.List;
import java.io.IOException;

import com.se206.g11.models.Settings;
import com.se206.g11.models.SpellingTopic;
import com.se206.g11.models.Word;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.effect.BoxBlur;

public class MainApp extends Application {
    private static Stage stage;
    private static StackPane stackPane;
    private static SpellingTopic chosenTopic;
    private static int score;
    private static List<Word> wordList;
    private static Settings settings;

    //// Private (helper) methods ////
    /**
     * set a new root application pane
     * @param fxml the name of the fxml file to load
     * @param title the title of the window to be spawned
     * @return a stage which can be set or spawned as a modal
     */
    private static void __setRoot(String fxml, String title) {
        SystemInterface.stopSpeech(); //Clear the queue
        try {
            stackPane = new StackPane();
            stackPane.getChildren().add(new FXMLLoader(MainApp.class.getResource("/fxml/" + fxml + ".fxml")).load());
            Scene scene = new Scene(stackPane);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();                    
        } catch (IOException e) {
            System.err.println("Unable to set root for fxml: " + fxml);
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
     * Set the current spelling topic
     * @param topic Spelling topic to get
     * @throws IOException throws if we are unable to read the topic file
     */
    public static void setTopic(SpellingTopic topic) throws IOException {
        chosenTopic = topic;
        wordList = SystemInterface.getWords(5, chosenTopic.getPath());
    }
    
    /**
     * Get the list of words
     * @return a list of words, can be null
     */
    public static List<Word> getWordList() {
        return wordList;
    }

    /**
     * Set the current score of the player to a new value
     * @param i score to be set
     */
    public static void setScore(int i) {
        score = i;
    }

    /**
     * Get the current score of the player.
     * @return the score of the player
     */
    public static int getScore() {
        return score;
    }
    
    /**
     * @param s the new settings to set for this user
     */
    public static void setSettings(Settings s) {
        settings = s;
        s.save("/.data/settings"); //TODO
    }

    /**
     * @return the current settings configuration
     */
    public static Settings getSettings() {
        return settings;
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
        SystemInterface.playSound("pop");
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
        settings = new Settings();
        stage.setResizable(false);
        setRoot("MenuScreen","Kemu Kupu");
    }

    /**
     * A cleanup method for when we close the application.
     */
    @Override
    public void stop() {
        SystemInterface.stopSpeech();
    }

    public static void main(String[] args) {
        launch(args);
    }
}