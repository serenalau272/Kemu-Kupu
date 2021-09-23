package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.se206.g11.ApplicationController;
import com.se206.g11.models.Language;
import com.se206.g11.models.Word;
import com.se206.g11.MainApp;
import com.se206.g11.SystemInterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameScreenController extends ApplicationController implements Initializable {
    private int wordIndex;

    @FXML
    TextField inputTextField;
    @FXML
    ImageView wordIndexBanner;

    public void inputTextField(){

    }

    private void updateWordIndexBanner() {
        try {
            Image img = new Image(new FileInputStream("src/main/resources/assets/Wordcount-labels/Word "+wordIndex+" of 5_.png"));

            wordIndexBanner.setImage(img);
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
            String word = MainApp.popWord(Language.MAORI);
            System.out.println(word);
            
            //attempt to test next word
            wordIndex++;
            testWord();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //retrieve word
        String word = MainApp.popWord(Language.MAORI);
        System.out.println(word);
        SystemInterface.readWord("Please spell:");
        SystemInterface.readWord(word, Language.MAORI);  

        wordIndex = 1;
        testWord();
    }    
}
