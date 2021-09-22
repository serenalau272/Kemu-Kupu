package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;

import com.se206.g11.ApplicationController;
import com.se206.g11.models.Word;
import com.se206.g11.MainApp;

import javafx.fxml.Initializable;

public class GameScreenController extends ApplicationController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // wordList = (ArrayList<Word>) SystemInterface.getWords(5, chosenTopic.getPath());

        for (Word word : MainApp.getWordList()) {
            System.out.println("English: " + word.getEnglish());
            System.out.println("Maori: " + word.getMaori());
        }
    }    
}
