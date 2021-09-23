package com.se206.g11.screens;
import java.net.URL;
import java.util.List;
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
import javafx.scene.input.MouseEvent;

public class GameScreenController extends ApplicationController implements Initializable {
    //Index of current word
    private int wordIndex = 0;
    //The words the user is to be tested on
    private List<Word> words;
    private boolean faulted = false;
    private boolean disabled = false;

    @FXML
    private TextField inputTextField;
    @FXML
    private ImageView wordIndexBanner;
    @FXML
    private ImageView progressBar;
    @FXML
    private ImageView hear_button;
    @FXML
    private ImageView settings_button;
    @FXML
    private ImageView submit_button;
    @FXML
    private ImageView skip_button;

    //// Helper Functions ////
    /**
     * Loads the next word for a user ot be tested on, simplifying the code elsewhere.
     * Functions:
     * - Resets variable for checking a fault
     * - Lodas the next word, clearing the textbox and relevant labels
     * - If no words are left, go to next screen.
     */
    private void __loadNextWord() {
        this.faulted = false;
        Word currWord = this.words.get(this.wordIndex);
        //Check if we have words left
        if (this.wordIndex < this.words.size() -1) {
            this.wordIndex++;
            this.inputTextField.clear();
            this.__hearAgain(1);
        } else {
            this.__disableQuiz();
            //Quiz finished, go to rewards screen?
            //TODO
        }
    }
    
    /**
     * Read a word to the user. Does not support language selection as this is not needed for A3.
     * @param repeats number of times to repeat the word to the user, minimum is 1.
     */
    private void __hearAgain(int repeats) {
        if (this.disabled) return;
        //SAFTEY: We have already validated that we are at a currently valid word, so a null pointer check isn't needed (or out of bounds check).
        SystemInterface.readWord(this.words.get(this.wordIndex).getMaori(), 1);
    }

    /**
     * Set the progress bar to a certain stage
     * @param i limited from 0-5, will set to level of completion based on provided value
     */
    private void __updateProgressBar(int i) {
        try {
            setImage(i, progressBar);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load progress bar for " + i + " error " + e);
        }
    }

    /**
     * Update the word index with the current value :)
     */
    private void __updateWordIndexBanner() {
        try {
            setImage(wordIndex, wordIndexBanner);
        } catch (FileNotFoundException exception){
            System.err.println("Unable to load banner for index: " + wordIndex);
        }
    }

    /**
     * Disable the quiz, due to an error or completion
     */
    private void __disableQuiz() {
        this.disabled = true;
        inputTextField.setDisable(true);
    }

    //// Button Handlers ////
    
    /**
     * Handler for the submit button
     */
    public void submitWordClick() {
        if (this.disabled) return;
        //check if they got the word right
        Word input = new Word();
        String t = inputTextField.getText();
        System.out.println("User wrote: " + t);
        input.setMaori(inputTextField.getText()); //Note: our Word implementation automatically strips and lowercases input

        if (this.words.get(this.wordIndex).isEqualLazy(input)) {
            //Correct
            int score = MainApp.getScore() + 1;
            this.__updateProgressBar(score);
            MainApp.setScore(score);
            //TODO specific actions for faulted words?
        } else {
            if (!this.faulted) {
                //First attempt wrong
                this.__hearAgain(2);
                this.faulted = true;
                //TODO show them second letter
            } else {
                //Second attempt wrong
                this.__loadNextWord();
            }
        }
    }

    /**
     * Handler for the skip button
     */
    public void skipWordClick() {
        if (this.disabled) return;
        this.__loadNextWord();
    }

    /**
     * Handler for the settings button
     */
    public void settingsClick() {
        //TODO, not sure how we want to implement this? Modals?
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();       

        //Load words from the MainApp
        this.words = MainApp.getWordList();
        if (this.words.size() == 0) {
            this.__disableQuiz();
            System.err.println("Unable to load words! Cannot start quiz");
            //TODO add popup
        } else {
            this.__hearAgain(1);
            this.__updateWordIndexBanner();
        }

        //initalize event handlers for buttons
        hear_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> __hearAgain(1));
        settings_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> settingsClick());
        submit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> submitWordClick());
        skip_button.addEventFilter(MouseEvent.MOUSE_CLICKED, _event -> skipWordClick());
    }    
}
