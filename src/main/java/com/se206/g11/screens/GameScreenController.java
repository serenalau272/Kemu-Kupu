package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.FileNotFoundException;

import com.se206.g11.ApplicationController;
import com.se206.g11.models.Language;
import com.se206.g11.models.Word;
import com.se206.g11.MainApp;
import com.se206.g11.SystemInterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GameScreenController extends ApplicationController implements Initializable {
    private int wordIndex;

    @FXML
    private TextField inputTextField;
    @FXML
    private ImageView wordIndexBanner;
    @FXML
    private ImageView progressBar;
    @FXML
    private ImageView hear_button;

    public void inputTextField(){

    }

    /**
     * Set the progress bar to a certain stage
     * @param i limited from 0-5, will set to level of completion based on provided value
     */
    private void updateProgressBar(Integer i) {
        try {
            setImage(i, progressBar);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load progress bar for " + i + " error " + e);
        }
    }

    private void updateWordIndexBanner() {
        try {
            setImage(wordIndex, wordIndexBanner);
        } catch (FileNotFoundException exception){
            System.err.println("Unable to load banner for index: " + wordIndex);
        }
    }

    private void testWord(){
        if (MainApp.getWordList().isEmpty()){
            //game finished
        } else {
            //continue game
            //update word banner
            updateWordIndexBanner();

            //retrieve word
            String word;
            
            word = MainApp.popWord(Language.MAORI);
            System.out.println(word);
            
            //attempt to test next word
            wordIndex++;
            testWord();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();        

        //retrieve word
        String word;
        word = MainApp.popWord(Language.MAORI);
        System.out.println(word);
        SystemInterface.readWord("Please spell:");
        SystemInterface.readWord(word, Language.MAORI);  

        wordIndex = 1;
        testWord();
    }    
}
