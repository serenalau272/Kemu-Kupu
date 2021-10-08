package com.se206.g11.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;

import com.se206.g11.ApplicationController;
import com.se206.g11.models.Game;
import com.se206.g11.models.Word;
import com.se206.g11.util.Sounds;
import com.se206.g11.MainApp;
import com.se206.g11.components.Clock;
import com.se206.g11.components.InputField;
import com.se206.g11.enums.Language;
import com.se206.g11.enums.Modals;
import com.se206.g11.enums.Status;
import com.se206.g11.enums.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;

public class Quiz extends ApplicationController implements Initializable {
    //Index of current word
    
    private Game game;

    private boolean disabled = false;
    private boolean awaitingInput = true;
    Clock timer;

    @FXML private Label messageLabel;
    @FXML private ImageView topicBanner; 
    @FXML private ImageView wordIndexBanner;
    @FXML private ImageView progressBar;
    @FXML private ImageView hear_button;
    @FXML private ImageView submit_button;
    @FXML private ImageView skip_button;
    @FXML private ImageView settings_button;
    @FXML private ImageView pause_button;
    @FXML private ImageView help_button;
    @FXML private ImageView responseImg;
    @FXML private ImageView continueLabel;
    @FXML private Arc arc;
    @FXML private Label timerLabel;
    @FXML private ImageView play_button;

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
        if (this.game.getWordIndex() < this.game.getWordListSize() -1) {
            this.game.setWordIndex(this.game.getWordIndex() + 1);
            InputField.configureInputField(this.game.getWord(), this);
            this.__updateWordIndexBanner();
            this.__hearWord(1);
        } else {
            //game ended, navigate to rewards screen
            MainApp.setRoot(View.RESULTS);
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
            switch (this.game.getWord().getStatus()) {
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
        Word word = this.game.getWord();
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
            
            MainApp.tts.readWord(this.game.getWord(), repeats, Language.MAORI);
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
            setImage(this.game.getWordIndex(), wordIndexBanner);
        } catch (FileNotFoundException exception){
            System.err.println("Unable to load banner for index: " + this.game.getWordIndex());
        }
    }

    private void setTopicBanner() {
        try {
            setImage(this.game.getTopic().getName(), topicBanner);
        } catch (FileNotFoundException exception) {
            System.err.println("Unable to load banner for topic: " + this.game.getTopic().getName());
        }
    }

    //// Button Handlers ////
    
    /**
     * Handler for the submit button
     */
    public void checkInput(Word input) {
        timer.stop();
        if (this.disabled) return;
        
        //return if empty textfield
        if (input.getMaori().isEmpty()) {
            __hearWord(1);
            return;
        }
        
        if (this.game.getWord().isEqualStrict(input)) {
            //Correct i.e. MASTERED. Increment score.
            this.game.getWord().setScoreMultiplier(timer.getScoreMultiplier());
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
        timer.start();

        if (this.game.getWord().getStatus() == Status.FAULTED) {
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

        //required to prevent bug
        this.game.getWord().setStatus(Status.SKIPPED);

        timer.stop();
        toggleLabels();
    }


    /**
     * insert macron to textfield
     */
    // private void insertMacron(String macron){
    //     String newInput = inputTextField.getText() + macron;
    //     inputTextField.setText(newInput);
    //     inputTextField.positionCaret(newInput.length());
    // }

    public void pauseClick() {
        MainApp.showModal(Modals.PAUSE);
    }

    public void onEnter(Word input){
        if (awaitingInput) {
            checkInput(input);
        } else if (continueLabel.isVisible() && !continueLabel.isDisabled()) {
            onEnterContinue();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.game = MainApp.getGameState();
        game.setWordIndex(0);
        setTopicBanner();

        //Inital setup & loading of data
        super.initialize();       
        showElementsForInput();      //no response given initially

        //Load words from the MainApp
        this.__hearWord(1);
        this.__updateWordIndexBanner();
        
        //configure timer
        timer = new Clock(arc, timerLabel);
        game.setClock(timer);
        timer.start();

        // initalize event handlers for buttons
        play_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            play_button.setVisible(false);
            InputField.configureInputField(game.getWord(), this);
        });

        settings_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            super.settingsClick();
        });

        pause_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            pauseClick();
        });

        hear_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> __hearWord(1));
    
        submit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            // checkInput();
            // TODO: fix
        });

        skip_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> skipWordClick());

        // TODO: fix
        // a_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ā"));
        // e_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ē"));
        // i_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ī"));
        // o_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ō"));
        // u_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> insertMacron("ū"));
    }    
}
