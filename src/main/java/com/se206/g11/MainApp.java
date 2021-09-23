package com.se206.g11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import com.se206.g11.models.Language;
import com.se206.g11.models.SpellingTopic;
import com.se206.g11.models.Word;

public class MainApp extends Application {
    private static Stage stage;

    private static SpellingTopic chosenTopic;
    private static int score;
    private static List<Word> wordList;

    //// Private (helper) methods ////
    private static void __setRoot(String fxml, String title, Integer delay) {
        //HACK this delay is bad, it's a blocking call which will freeze the entire ui.
        //I'd really like to not be delaying actions in this way, is there a better solution?
        try {
            if (delay != null) Thread.sleep(delay);
            Scene scene = new Scene(loadFXML(fxml));
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();                    
        } catch (InterruptedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //// Public Methods ////
    public static void setTopic(SpellingTopic topic) throws IOException {
        chosenTopic = topic;
        wordList = SystemInterface.getWords(5, chosenTopic.getPath());
    }

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
     * @return
     */
    public static int getScore() {
        return score;
    }

    @Override
    public void start(@SuppressWarnings("exports") Stage s) {
        stage = s;
        stage.setResizable(false);
        setRoot("MenuScreen","Kemu Kupu");
    }

    /**
     * Change which scene the user is looking at.
     * @param fxml the name of the scene to load
     * @param title the title of the window to set
     */
    public static void setRoot(String fxml, String title) {
        __setRoot(fxml, title, null);
    }

    /**
     * Change which scene the user is looking at.
     * @param fxml the name of the scene to load
     * @param title the title of the window to set
     * @param isDelay delay the page change for a number of seconds.
     */
    public static void setRoot(String fxml, String title, Boolean isDelay) {
        __setRoot(fxml, title, 5000);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Load and show a modal to the user
     * @param fxml the name of the fxml to load in a modal
     * @param title the title of the modal window
     */
    //TODO avoid duplicate code here, merge functionality __setRoot. This should improve readability & extendability of code. For MVP is fine though.
    public static void showModal(String fxml, String title) {
        Stage d = new Stage();
        Scene s;
        try {
            s = new Scene(loadFXML(fxml));
        } catch (Exception e) {
            System.err.println("Unable to load modal " + fxml + " due to error " + e.toString());
            return;
        }
        d.setScene(s);
        d.setTitle(title);
        d.initModality(Modality.APPLICATION_MODAL);
        d.showAndWait();
    }
}