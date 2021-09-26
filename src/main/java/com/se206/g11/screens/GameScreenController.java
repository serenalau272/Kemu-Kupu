package com.se206.g11.screens;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;

import com.se206.g11.ApplicationController;
import com.se206.g11.models.Status;
import com.se206.g11.models.Word;
import com.se206.g11.MainApp;
import com.se206.g11.SystemInterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameScreenController extends ApplicationController implements Initializable {
    //Index of current word
    private int wordIndex = 0;
    private Status status = Status.NONE;
    //The words the user is to be tested on
    private List<Word> words;
    private boolean disabled = false;
    private boolean awaitingResponse = true;

    @FXML private TextField inputTextField;
    @FXML private Label hintLabel;
    @FXML private ImageView wordIndexBanner;
    @FXML private ImageView progressBar;
    @FXML private ImageView hear_button;
    @FXML private ImageView settings_button;
    @FXML private ImageView submit_button;
    @FXML private ImageView skip_button;
    @FXML private ImageView responseImg;
    @FXML private ImageView continueLabel;
    @FXML private ImageView progressMsg;

    //macron buttons
    @FXML private ImageView a_button, e_button, i_button, o_button, u_button;

    //// Helper Functions ////
    /**
     * Loads the next word for a user ot be tested on, simplifying the code elsewhere.
     * Functions:
     * - Loads the next word, and relevant labels
     * - If no words are left, go to rewards screen.
     */
    private void __loadNextWord() {
        this.status = Status.NONE;
        //Check if we have words left
        if (this.wordIndex < this.words.size() -1) {
            this.wordIndex++;
            this.__updateWordIndexBanner();
            this.__hearWord(1);
        } else {
            //game ended, navigate to rewards screen
            this.__disableQuiz();
            MainApp.showModal("RewardScreen", "Reward Screen");
        }
    }
    
    /**
     * shows appropriate ImageViews based upon whether one is awaiting a response
     */
    private void toggleLabels(){
        if (awaitingResponse){
            showOnResponse();
        } else {
            showOffResponse();
        }
        awaitingResponse = !awaitingResponse;
    }

    /**
     * shows appropriate ImageViews when response given, and awaiting 'continue'
     */
    private void showOnResponse(){
        //remove buttons visibility
        submit_button.setVisible(false);
        skip_button.setVisible(false);
        hear_button.setVisible(false);

        this.inputTextField.clear();

        //setup response image
        try {
            switch (status) {
                case SKIPPED:
                    setImage("SKIPPED", responseImg);
                    break;
                case FAULTED:
                    setImage("INCORRECT_2", responseImg);
                    break;
                case FAILED:
                    setImage("INCORRECT_3", responseImg);
                    progressMsg.setVisible(true);
                    break;
                default:
                    //mastered or none
                    setImage("CORRECT", responseImg);
                    break;
            }
        } catch (FileNotFoundException exception){
            System.err.println("File not found");
        }

        addSubTextIncorrect();

        //show continue and response
        responseImg.setVisible(true);
        continueLabel.setVisible(true);
        hintLabel.setVisible(true);
    }

    /**
     * shows appropriate ImageViews when awaiting response
     */
    private void showOffResponse(){
        //remove buttons visibility
        submit_button.setVisible(true);
        skip_button.setVisible(true);
        hear_button.setVisible(true);

        //show continue and response
        responseImg.setVisible(false);
        continueLabel.setVisible(false);
        hintLabel.setVisible(false);
        progressMsg.setVisible(false);
    }

    /**
     * Add subtext for faulted and failed responses
     */
    private void addSubTextIncorrect() {
        String word = this.words.get(this.wordIndex).getMaori();
        switch (this.status) {
            case FAULTED:
                hintLabel.setText("Hint: Second letter is '" + word.charAt(1) + "'");
                break;
            case FAILED: 
                hintLabel.setText("Correct answer: " + word);
                break;
            default: 
                hintLabel.setText("");
                break;
        }
    }

    /**
     * Read a word to the user. Does not support language selection as this is not needed for A3.
     * @param repeats number of times to repeat the word to the user, minimum is 1.
     */
    private void __hearWord(int repeats) {
        if (this.disabled) return;
        //SAFTEY: We have already validated that we are at a currently valid word, so a null pointer check isn't needed (or out of bounds check).
        SystemInterface.readWord(this.words.get(this.wordIndex).getMaori(), repeats);
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
        this.inputTextField.setDisable(true);
        // MainApp.showModal("RewardScreen", "Reward Screen");
    
    }

    //// Button Handlers ////
    
    /**
     * Handler for the submit button
     */
    public void checkInput() {
        if (this.disabled) return;
        
        //return if empty textfield
        if (this.inputTextField.getText().isEmpty()) {
            inputTextField.setEditable(true);
            return;
        }

        //check if they got the word right
        Word input = new Word();
        input.setMaori(this.inputTextField.getText()); //Note: our Word implementation automatically strips and lowercases input
        
        if (this.words.get(this.wordIndex).isEqualStrict(input)) {
            //Correct i.e. MASTERED. Increment score.
            SystemInterface.playSound("correct");
            int score = MainApp.getScore() + 20;
            this.__updateProgressBar(score / 20);
            MainApp.setScore(score);

            this.status = Status.MASTERED;
        } else {
            if (status == Status.NONE || status == Status.SKIPPED) {
                //First attempt wrong i.e. FAULTED
                this.status = Status.FAULTED;
            } else {
                //Second attempt wrong. i.e. FAILED
                this.status = Status.FAILED;
            }
        }

        toggleLabels();
    }

    /**
     * function to continue from response given for word, determined by status
     */
    public void onEnterContinue(){
        if (this.disabled) return;
        toggleLabels();
        inputTextField.setEditable(true);

        if (status == Status.FAULTED){
            this.__hearWord(1);
        }

        if (status == Status.FAILED || status == Status.MASTERED || status == Status.SKIPPED){
            //load next word
            __loadNextWord();
        }

    }

    /**
     * Handler for the skip button
     */
    public void skipWordClick() {
        if (this.disabled) return;
        this.status = Status.SKIPPED;
        toggleLabels();
    }

    /**
     * Handler for the settings button
     */
    public void settingsClick() {
        MainApp.showModal("SettingScreen", "Settings");
    }

    /**
     * insert macron to textfield
     */
    private void insertMacron(String macron){
        String newInput = inputTextField.getText() + macron;
        inputTextField.setText(newInput);
        inputTextField.positionCaret(newInput.length());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();       
        showOffResponse();      //no response given initially

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
        settings_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            SystemInterface.playSound("pop");
            settingsClick();
        });

        submit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            inputTextField.setEditable(false);
            checkInput();
        });

        skip_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            inputTextField.setEditable(false);
            skipWordClick();
        });
        
        inputTextField.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (awaitingResponse) {
                    inputTextField.setEditable(false);
                    checkInput();
                } else if (continueLabel.isVisible() && !continueLabel.isDisabled()) {
                    onEnterContinue();
                }
            }
        });

        a_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ā"));
        e_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ē"));
        i_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ī"));
        o_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ō"));
        u_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ū"));
    }    
}
