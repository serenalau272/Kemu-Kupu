package com.se206.g11.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;

import com.se206.g11.ApplicationController;
import com.se206.g11.models.Game;
import com.se206.g11.models.Language;
import com.se206.g11.models.Modals;
import com.se206.g11.models.Status;
import com.se206.g11.models.Word;
import com.se206.g11.util.Sounds;
import com.se206.g11.MainApp;
import com.se206.g11.components.Clock;
import com.se206.g11.components.InputTile;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;

public class Quiz extends ApplicationController implements Initializable {
    //Index of current word
    private int wordIndex = 0;
    private Game game;

    private boolean disabled = false;
    private boolean awaitingInput = true;
    Clock timer;

    @FXML private TextField inputTextField;
    @FXML private Label messageLabel;
    @FXML private ImageView wordIndexBanner;
    @FXML private ImageView progressBar;
    @FXML private ImageView hear_button;
    @FXML private ImageView settings_button;
    @FXML private ImageView submit_button;
    @FXML private ImageView skip_button;
    @FXML private ImageView responseImg;
    @FXML private ImageView continueLabel;
    @FXML private Arc arc;
    @FXML private Label timerLabel;

    //macron buttons
    @FXML private ImageView macron_bg;
    @FXML private ImageView a_button;
    @FXML private ImageView e_button;
    @FXML private ImageView i_button;
    @FXML private ImageView o_button;
    @FXML private ImageView u_button;

    //// Helper Functions ////
    /**
     * Loads the next word for a user ot be tested on, simplifying the code elsewhere.
     * Functions:
     * - Loads the next word, and relevant labels
     * - If no words are left, go to rewards screen.
     */
    private void __loadNextWord() {
        //Check if we have words left
        if (this.wordIndex < this.game.getWordListSize() -1) {
            this.wordIndex++;
            this.__updateWordIndexBanner();
            this.__hearWord(1);
        } else {
            //game ended, navigate to rewards screen
            this.__disableQuiz();
            MainApp.showModal(Modals.REWARD);
        }
    }
    
    /**
     * shows appropriate ImageViews based upon whether one is awaiting a response
     */
    private void toggleLabels(){
        awaitingInput = !awaitingInput;

        if (awaitingInput){
            showElementsForInput();
        } else {
            showElementsForResponse();
        }
    }

    /**
     * shows appropriate ImageViews when response given, and awaiting 'continue'
     */
    private void showElementsForResponse(){
        this.inputTextField.clear();

        setResponseImageView();
        addSubTextIncorrect();

        //hide elements
        submit_button.setVisible(false);
        skip_button.setVisible(false);
        hear_button.setVisible(false);
        setMacronVisibility(false);

        //show elements
        responseImg.setVisible(true);
        continueLabel.setVisible(true);
        messageLabel.setVisible(true);
    }

    /**
     * shows appropriate ImageViews when awaiting response
     */
    private void showElementsForInput(){
        //show elements
        submit_button.setVisible(true);
        skip_button.setVisible(true);
        hear_button.setVisible(true);
        setMacronVisibility(true);

        //hide elements
        responseImg.setVisible(false);
        continueLabel.setVisible(false);
        messageLabel.setVisible(false);
    }

    private void setMacronVisibility(boolean isVisible){
        a_button.setVisible(isVisible);
        e_button.setVisible(isVisible);
        i_button.setVisible(isVisible);
        o_button.setVisible(isVisible);
        u_button.setVisible(isVisible);
        macron_bg.setVisible(isVisible);
    }

    /**
     * replaces response image with appropriate image based upon status
     */
    private void setResponseImageView(){
        try {
            switch (this.game.getWord(this.wordIndex).getStatus()) {
                case SKIPPED:
                    setImage("SKIPPED", responseImg);
                    break;
                case FAULTED:
                    setImage("INCORRECT_2", responseImg);
                    break;
                case FAILED:
                    setImage("INCORRECT_3", responseImg);
                    break;
                default:
                    //mastered or none
                    setImage("CORRECT", responseImg);
                    break;
            }
        } catch (FileNotFoundException exception){
            System.err.println("File not found");
        }
    }

    /**
     * Add subtext for faulted and failed responses
     */
    private void addSubTextIncorrect() {
        Word word = this.game.getWord(this.wordIndex);
        switch (word.getStatus()) {
            case FAULTED:
                messageLabel.setText("Hint: Translation is '" + word.getEnglish() + "'");
                break;
            case FAILED: 
                messageLabel.setText("Correct answer: " + word.getMaori());
                break;
            default: 
                messageLabel.setText("");
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
        try {
            
            MainApp.tts.readWord(this.game.getWord(this.wordIndex), repeats, Language.MAORI);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        timer.stop();
        this.disabled = true;
        this.inputTextField.setDisable(true);
    }

    //// Button Handlers ////
    
    /**
     * Handler for the submit button
     */
    public void checkInput() {
        timer.stop();
        
        if (this.disabled) return;
        
        //return if empty textfield
        if (this.inputTextField.getText().isEmpty()) {
            inputTextField.setEditable(true);
            __hearWord(1);
            return;
        }

        //check if they got the word right
        Word input = new Word();
        input.setMaori(this.inputTextField.getText()); //Note: our Word implementation automatically strips and lowercases input
        
        if (this.game.getWord(this.wordIndex).isEqualStrict(input)) {
            //Correct i.e. MASTERED. Increment score.
            this.game.getWord(this.wordIndex).setTimeMultiplier(timer.getScoreMultiplier());
            Sounds.playSoundEffect("correct");
            this.__updateProgressBar(this.game.getScore());
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
        timer.start();

        if (this.game.getWord(this.wordIndex).getStatus() == Status.FAULTED) {
            this.__hearWord(1);
        } else {
            __loadNextWord();
        }
    }

    /**
     * Handler for the skip button
     */
    public void skipWordClick() {
        if (this.disabled) return;

        timer.stop();
        toggleLabels();
    }

    /**
     * Handler for the settings button
     */
    public void settingsClick() {
        MainApp.showModal(Modals.SETTING);
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
        this.game = MainApp.getGameState();
        //Inital setup & loading of data
        super.initialize();       
        showElementsForInput();      //no response given initially

        //Load words from the MainApp
        this.__hearWord(1);
        this.__updateWordIndexBanner();
        
        //configure timer
        timer = new Clock(arc, timerLabel);
        timer.start();

        //create textfield
        Scene stage = messageLabel.getScene();
        StackPane r = (StackPane) stage.getRoot();
        InputTile t = new InputTile(20, 30);
        // r.getChildren().addAll(t);

        // initalize event handlers for buttons
        hear_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> __hearWord(1));
        settings_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
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
                if (awaitingInput) {
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
