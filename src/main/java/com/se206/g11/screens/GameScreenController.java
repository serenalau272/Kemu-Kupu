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
import javafx.scene.input.MouseEvent;

public class GameScreenController extends ApplicationController implements Initializable {
    private int wordIndex;

    @FXML
    private TextField inputTextField;
    @FXML
    private ImageView wordIndexBanner;
    @FXML
    private ImageView progressbar;

    @FXML
    private ImageView hear_button;

    /**
     * Set the progress bar to a % value.
     * @param v a integer between 0 and 5 indicating where the user is up to
     */
    private void setProgressBar(Integer v) {
        if (v < 0 || v > 5) {
            System.err.print("Index provided was out of range! Got '" + v + "' expected 0-5");
            return;
        }
        //HACK this could fail on build as we are statically typing the path!
        Image i = new Image("file:src/main/resources/assets/progressBar/" + v + ".png");
        progressbar.setImage(i);
    }

    public void inputTextField(){

    }

    private void updateWordIndexBanner() {
        try {
            Image img = new Image(new FileInputStream("src/main/resources/assets/Wordcount-labels/Word " + wordIndex + " of 5_.png"));

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
