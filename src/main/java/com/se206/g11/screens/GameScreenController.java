package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;

import com.se206.g11.ApplicationController;
import com.se206.g11.models.Language;
import com.se206.g11.models.Word;
import com.se206.g11.MainApp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class GameScreenController extends ApplicationController implements Initializable {
    @FXML
    TextField inputTextField;

    public void inputTextField(){

    }

    private void testWord(){
        if (MainApp.getWordList().isEmpty()){
            //game finished
        } else {
            //continue game
            String word = MainApp.popWord(Language.MAORI);
            System.out.println(word);

            //attempt to test next word
            testWord();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testWord();
    }    
}
