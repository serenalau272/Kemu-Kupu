package com.se206.g11.controllers;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;

import com.se206.g11.ApplicationController;
import com.se206.g11.models.Game;
import com.se206.g11.models.Word;
import com.se206.g11.util.Sounds;
import com.se206.g11.MainApp;
import com.se206.g11.components.Clock;
import com.se206.g11.components.InputField;
import com.se206.g11.enums.Gamemode;
import com.se206.g11.enums.Language;
import com.se206.g11.enums.Modals;
import com.se206.g11.enums.Status;
import com.se206.g11.enums.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;

public class Quiz extends ApplicationController implements Initializable {
    private String[] correctMessages = {"Good Job! You're doing great!", "Ka mau te wehi!", "Superb! You're a spelling superstar!", "Bzzzz... Spelling Bee here!", "Do the mahi! Get the treats!"};
    private String[] incorrectMessages = {"No sweat! Practice makes progress!", "You've got this! Try again next time!", "Third times the charm!"};
    
    private Game game;

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
    @FXML private ImageView clock;
    @FXML private ImageView score;

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
            InputField.reconfigureInputField(this.game.getWord());
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
        addSubText();
        

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
        arc.setVisible(true);
        timerLabel.setVisible(true);
        wordIndexBanner.setVisible(true);

        //hide elements
        responseImg.setVisible(false);
        continueLabel.setVisible(false);
        messageLabel.setVisible(false);
        score.setVisible(false);
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
    private void addSubText() {
        Word word = this.game.getWord();
        switch (word.getStatus()) {
            case FAULTED:
                messageLabel.setText("Hint: Translation is '" + word.getEnglish() + "'");
                break;
            case FAILED:
                String incorrectMsg = incorrectMessages[new Random().nextInt(incorrectMessages.length)];
                messageLabel.setText(incorrectMsg);
                break;
            case MASTERED:
                String correctMsg = correctMessages[new Random().nextInt(correctMessages.length)];
                messageLabel.setText(correctMsg);
                break;
            default: 
                messageLabel.setText("");
                break;
        }
    }

    private void setScoreIncrease(int scoreIncrease) {
        try {
            setImage(scoreIncrease, this.score);
            this.score.setVisible(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a word to the user. Does not support language selection as this is not needed for A3.
     * @param repeats number of times to repeat the word to the user, minimum is 1.
     */
    private void __hearWord(int repeats) {
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
    public void checkInput(Word input, boolean isInputEmpty) {
        timer.stop();
        
        //return if empty textfield
        if (isInputEmpty) {
            __hearWord(1);
            return;
        }

        InputField.setEditability(false);
        
        if (this.game.getWord().isEqualStrict(input)) {
            //Correct i.e. MASTERED. Increment score.
            this.game.getWord().setScoreMultiplier(timer.getScoreMultiplier());
            Sounds.playSoundEffect("correct");
            this.__updateProgressBar(this.game.getScore());
            this.setScoreIncrease(timer.getScoreMultiplier() * 5);
        }

        toggleLabels();
    }

    /**
     * function to continue from response given for word, determined by status
     */
    public void onEnterContinue(){       
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
        //required to prevent bug
        this.game.getWord().setStatus(Status.SKIPPED);
        InputField.setEditability(false);

        timer.stop();
        toggleLabels();
    }

    public void pauseClick() {
        MainApp.showModal(Modals.PAUSE);
    }

    public void onEnter(Word input, boolean isInputEmpty){
        if (awaitingInput) {
            checkInput(input, isInputEmpty);
        } else if (continueLabel.isVisible() && !continueLabel.isDisabled()) {
            InputField.setEditability(true);
            InputField.reconfigureInputField(this.game.getWord());;
            
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
        MainApp.disableScreenNodes(true);    
        play_button.setDisable(false); 

        // initalize event handlers for buttons
        play_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            showElementsForInput();      //no response given initially

            //Load words from the MainApp
            this.__updateWordIndexBanner();
            //configure timer
            timer = new Clock(arc, timerLabel);
            game.setClock(timer);

            if (this.game.getGameMode() == Gamemode.PRACTICE){
                
                arc.setVisible(false);
                timerLabel.setVisible(false);
                try {
                    setImage("Practice", clock);
                    clock.setFitWidth(1400);
                    clock.setTranslateX(-225);
                } catch (FileNotFoundException e){
                    System.err.println("Unable to load practice clock image");
                }

                Sounds.playMusic("practice");
            } else {
                Sounds.playMusic("game");
            }
            
            this.__hearWord(1);
            timer.start();
            Sounds.playSoundEffect("pop");
            play_button.setVisible(false);
            MainApp.disableScreenNodes(false);
            InputField.configureInputField(game.getWord(), this, submit_button);
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

        skip_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> skipWordClick());

        a_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ā"));
        e_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ē"));
        i_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ī"));
        o_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ō"));
        u_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ū"));
    }    
}
