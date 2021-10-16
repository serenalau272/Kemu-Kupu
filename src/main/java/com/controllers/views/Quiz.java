package com.controllers.views;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;

import com.models.Game;
import com.models.Word;
import com.util.Sounds;
import com.App;
import com.components.InputField;
import com.components.animations.Clock;
import com.controllers.ApplicationController;
import com.enums.Gamemode;
import com.enums.Language;
import com.enums.Modals;
import com.enums.Status;
import com.enums.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;

public class Quiz extends ApplicationController implements Initializable {
    private String[] correctMessages = { "Good Job! You're doing great!", "Ka mau te wehi!",
            "Superb! You're a spelling superstar!", "Bzzzz... Spelling Bee here!", "Do the mahi! Get the treats!" };
    private String[] incorrectMessages = { "No sweat! Practice makes progress!",
            "You've got this! Try again next time!", "Third times the charm!" };

    private Game game;
    Quiz controller;

    private boolean awaitingInput = true;
    Clock timer;

    @FXML
    private Label messageLabel;
    @FXML
    private ImageView topicBanner;
    @FXML
    private ImageView wordIndexBanner;
    @FXML
    private ImageView progressBar;
    @FXML
    private ImageView hearButton;
    @FXML
    private ImageView submitButton;
    @FXML
    private ImageView skipButton;
    @FXML
    private ImageView settingsButton;
    @FXML
    private ImageView pauseButton;
    @FXML
    private ImageView helpButton;
    @FXML
    private ImageView responseImg;
    @FXML
    private ImageView continueLabel;
    @FXML
    private Arc arc;
    @FXML
    private Label timerLabel;
    @FXML
    private ImageView clock;
    @FXML
    private ImageView score;
    @FXML
    private ImageView practiceSign;

    // macron buttons
    @FXML
    private ImageView macronBackground;
    @FXML
    private ImageView aButton;
    @FXML
    private ImageView eButton;
    @FXML
    private ImageView iButton;
    @FXML
    private ImageView oButton;
    @FXML
    private ImageView uButton;

    //// Helper Functions ////
    /**
     * Loads the next word for a user ot be tested on, simplifying the code
     * elsewhere. Functions: - Loads the next word, and relevant labels - If no
     * words are left, go to rewards screen.
     */
    private void __loadNextWord() {
        // Check if we have words left
        if (this.game.getWordIndex() < this.game.getWordListSize() - 1) {
            this.game.setWordIndex(this.game.getWordIndex() + 1);
            InputField.reconfigureInputField(this.game.getWord());
            this.__updateWordIndexBanner();
            this.__hearWord(1);
        } else {
            // game ended, navigate to rewards screen
            App.setRoot(View.RESULTS);

        }
    }

    /**
     * shows appropriate ImageViews based upon whether one is awaiting a response
     */
    private void toggleLabels() {
        awaitingInput = !awaitingInput;

        if (awaitingInput) {
            showElementsForInput();
        } else {
            showElementsForResponse();
        }
    }

    /**
     * shows appropriate ImageViews when response given, and awaiting 'continue'
     */
    private void showElementsForResponse() {
        setResponseImageView();
        addSubText();

        // hide elements
        submitButton.setVisible(false);
        skipButton.setVisible(false);
        hearButton.setVisible(false);
        setMacronVisibility(false);

        // show elements
        responseImg.setVisible(true);
        continueLabel.setVisible(true);
        messageLabel.setVisible(true);
    }

    /**
     * shows appropriate ImageViews when awaiting response
     */
    private void showElementsForInput() {
        // show elements
        submitButton.setVisible(true);
        skipButton.setVisible(true);
        hearButton.setVisible(true);
        setMacronVisibility(true);
        wordIndexBanner.setVisible(true);

        // hide elements
        responseImg.setVisible(false);
        continueLabel.setVisible(false);
        messageLabel.setVisible(false);
        score.setVisible(false);
    }

    private void setMacronVisibility(boolean isVisible) {
        aButton.setVisible(isVisible);
        eButton.setVisible(isVisible);
        iButton.setVisible(isVisible);
        oButton.setVisible(isVisible);
        uButton.setVisible(isVisible);
        macronBackground.setVisible(isVisible);
    }

    /**
     * replaces response image with appropriate image based upon status
     */
    private void setResponseImageView() {
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
                    // mastered or none
                    setImage("CORRECT", responseImg);
                    break;
            }
        } catch (FileNotFoundException exception) {
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
     * Read a word to the user. Does not support language selection as this is not
     * needed for A3.
     * 
     * @param repeats number of times to repeat the word to the user, minimum is 1.
     */
    private void __hearWord(int repeats) {
        // SAFTEY: We have already validated that we are at a currently valid word, so a
        // null pointer check isn't needed (or out of bounds check).
        try {

            App.tts.readWord(this.game.getWord(), repeats, Language.MAORI);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Set the progress bar to a certain stage
     * 
     * @param i limited from 0-5, will set to level of completion based on provided
     *          value
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
        } catch (FileNotFoundException exception) {
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

        // return if empty textfield
        if (isInputEmpty) {
            __hearWord(1);
            return;
        }

        InputField.setEditability(false);

        if (this.game.getWord().isEqualStrict(input)) {
            // Correct i.e. MASTERED. Increment score.
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
    public void onEnterContinue() {
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
        // required to prevent bug
        this.game.getWord().setStatus(Status.SKIPPED);
        InputField.setEditability(false);

        timer.stop();
        toggleLabels();
    }

    public void pauseClick() {
        App.showModal(Modals.PAUSE);
    }

    public void helpClick() {
        App.showModal(Modals.HELP);
    }

    public void onEnter(Word input, boolean isInputEmpty) {
        if (awaitingInput) {
            checkInput(input, isInputEmpty);
        } else if (continueLabel.isVisible() && !continueLabel.isDisabled()) {
            InputField.setEditability(true);
            InputField.reconfigureInputField(this.game.getWord());
            ;

            onEnterContinue();
        }
    }

    @Override
    protected void start() {
        showElementsForInput(); // no response given initially

        if (game.getGameMode() == Gamemode.PRACTICE) {
            practiceSign.setVisible(true);
            clock.setVisible(false);
            Sounds.playMusic("practice");
        } else {
            arc.setVisible(true);
            timerLabel.setVisible(true);
            Sounds.playMusic("game");
        }

        __hearWord(1);
        timer.start();
        Sounds.playSoundEffect("pop");
        InputField.configureInputField(game.getWord(), controller, submitButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inital setup & loading of data
        super.initialize();

        this.game = App.getGameState();
        game.setWordIndex(0);
        setTopicBanner();
        // Load words from the MainApp
        this.__updateWordIndexBanner();
        // configure timer
        timer = new Clock(arc, timerLabel);
        App.clock = timer;
        controller = this;

        settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            super.settingsClick();
        });

        pauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            pauseClick();
        });

        helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            helpClick();
        });

        hearButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> __hearWord(1));

        skipButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> skipWordClick());

        aButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ā"));
        eButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ē"));
        iButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ī"));
        oButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ō"));
        uButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ū"));
    }
}
