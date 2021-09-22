package com.se206.g11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;
import java.io.IOException;
import com.se206.g11.models.SpellingTopic;
import com.se206.g11.models.Word;

public class MainApp extends Application {
    private static Stage stage;

    private static SpellingTopic chosenTopic;
    private static int score;
    private static List<Word> wordList;

    public static void setTopic(SpellingTopic topic) throws IOException {
        chosenTopic = topic;
        wordList = SystemInterface.getWords(5, chosenTopic.getPath());
    }

    public static List<Word> getWordList() {
        return wordList;
    }

    @Override
    public void start(@SuppressWarnings("exports") Stage s) {
        stage = s;
        stage.setResizable(false);
        setRoot("MenuScreen","Kemu Kupu");
    }

    public static void setRoot(String fxml, String title) {
        try {
            Scene scene = new Scene(loadFXML(fxml));
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            System.err.println("Unable to load FXML file: " + fxml + "Error: " + e);
        }
    }

    public static void setRoot(String fxml, String title, Boolean isDelay) {
        try {
            Scene scene = new Scene(loadFXML(fxml));
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            if (isDelay) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                };
            }
        } catch (IOException exception){
            System.err.println("Unable to load FXML file: " + fxml);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}