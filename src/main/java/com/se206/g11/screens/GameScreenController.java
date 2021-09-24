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
    @FXML
    private ImageView responseImg;
    @FXML
    private ImageView continue_button;

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
        // Word currWord = this.words.get(this.wordIndex);
        //Check if we have words left
        if (this.wordIndex < this.words.size() -1) {
            this.wordIndex++;
            this.inputTextField.clear();
            this.__updateWordIndexBanner();
            this.__hearWord(1);
        } else {
            this.__disableQuiz();
            //Quiz finished, go to rewards screen?
            //TODO
        }
    }
    
    private void showOnResponse(){
        //remove buttons visibility
        submit_button.setVisible(false);
        skip_button.setVisible(false);
        hear_button.setVisible(false);

        //show continue and response
        responseImg.setVisible(true);
        continue_button.setVisible(true);
    }


    private void showOffResponse(){
        //remove buttons visibility
        submit_button.setVisible(true);
        skip_button.setVisible(true);
        hear_button.setVisible(true);

        //show continue and response
        responseImg.setVisible(false);
        continue_button.setVisible(false);
    }


    /**
     * Read a word to the user. Does not support language selection as this is not needed for A3.
     * @param repeats number of times to repeat the word to the user, minimum is 1.
     */
    private void __hearWord(int repeats) {
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
            setImage(this.wordIndex, wordIndexBanner);
        } catch (FileNotFoundException exception){
            System.err.println("Unable to load banner for index: " + this.wordIndex);
        }
    }

    /**
     * Disable the quiz, due to an error or completion
     */
    private void __disableQuiz() {
        this.disabled = true;
        this.inputTextField.setText("Quiz Disabled");
        this.inputTextField.setDisable(true);
    }

    //// Button Handlers ////
    
    /**
     * Handler for the submit button
     */
    public void submitWordClick() {
        if (this.disabled) return;
        //check if they got the word right
        Word input = new Word();
        input.setMaori(this.inputTextField.getText()); //Note: our Word implementation automatically strips and lowercases input

        if (this.words.get(this.wordIndex).isEqualLazy(input)) {
            //Correct
            System.out.println("Correct");
            int score = MainApp.getScore() + 20;
            this.__updateProgressBar(score / 20);
            MainApp.setScore(score);

            //TODO specific actions for faulted words?
            // if (this.faulted) {}

            this.__loadNextWord();
        } else {
            if (!this.faulted) {
                //First attempt wrong
                this.__hearWord(2);
                this.faulted = true;
                //TODO show them second letter

            } else {
                //Second attempt wrong
                this.__loadNextWord();
            }
        }

        showOnResponse();
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
        showOffResponse();      //no response given

        //Load words from the MainApp
        this.words = MainApp.getWordList();
        if (this.words.size() == 0) {
            this.__disableQuiz();
            System.err.println("Unable to load words! Cannot start quiz");
            //TODO add popup
        } else {
            MainApp.setScore(0);
            this.__hearWord(1);
            this.__updateWordIndexBanner();
        }

        //initalize event handlers for buttons
        hear_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> __hearWord(1));
        settings_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> settingsClick());
        submit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> submitWordClick());
        skip_button.addEventFilter(MouseEvent.MOUSE_CLICKED, _event -> skipWordClick());
    }    
}
