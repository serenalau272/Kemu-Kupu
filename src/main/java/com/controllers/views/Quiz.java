package com.controllers.views;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;

import com.models.Game;
import com.models.Word;
import com.util.Modal;
import com.util.Sounds;
import com.MainApp;
import com.components.InputField;
import com.components.animations.Clock;
import com.controllers.ApplicationController;
import com.enums.ConfirmModal;
import com.enums.Gamemode;
import com.enums.Language;
import com.enums.Modals;
import com.enums.Status;
import com.enums.Views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;

/**
 * This class is the controller for the quiz
 */
public class Quiz extends ApplicationController implements Initializable {
    //// Constants ////
    private final String[] correctMessages = { "Good Job! You're doing great!", "Ka mau te wehi!",
            "Superb! You're a spelling superstar!", "Bzzzz... Spelling Bee here!", "Do the mahi! Get the treats!" };
    private final String[] incorrectMessages = { "No sweat! Practice makes progress!",
            "You've got this! Try again next time!", "Third times the charm!" };

    //// Properties ////

    private Game game;
    private Quiz controller;

    private Clock timer;

    @FXML
    private Label message;
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
    private ImageView menuButton;
    @FXML
    private ImageView responseImg;
    @FXML
    private ImageView continueLabel;
    @FXML
    private Arc arc;
    @FXML
    private Label timerText;
    @FXML
    private ImageView clock;
    @FXML
    private ImageView score;
    @FXML
    private ImageView practiceSign;
    @FXML
    private ImageView avatar;
    @FXML
    private Label avatarMessage;
    @FXML
    private ImageView speechBubble;

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
            MainApp.setRoot(Views.RESULTS);

        }
    }

    /**
     * shows appropriate ImageViews based upon whether one is awaiting a response
     */
    private void __toggleLabels() {
        this.game.setAwaitingInput(!this.game.getAwaitingInput());

        if (this.game.getAwaitingInput()) {
            this.__showElementsForInput();
        } else {
            this.__showElementsForResponse();
        }
    }

    /**
     * shows appropriate ImageViews when response given, and awaiting 'continue'
     */
    private void __showElementsForResponse() {
        this.__setResponseImageView();
        this.__addSubText();

        // hide elements
        this.submitButton.setVisible(false);
        this.skipButton.setVisible(false);
        this.hearButton.setVisible(false);
        this.__setMacronVisibility(false);

        // show elements
        this.responseImg.setVisible(true);
        this.continueLabel.setVisible(true);
        this.message.setVisible(true);
    }

    /**
     * shows appropriate ImageViews when awaiting response
     */
    private void __showElementsForInput() {
        this.avatarMessage.setText("");

        // show elements
        this.submitButton.setVisible(true);
        this.skipButton.setVisible(true);
        this.hearButton.setVisible(true);
        this.__setMacronVisibility(true);
        this.wordIndexBanner.setVisible(true);

        // hide elements
        this.responseImg.setVisible(false);
        this.continueLabel.setVisible(false);
        this.message.setVisible(false);
        this.score.setVisible(false);
        this.speechBubble.setVisible(false);
    }

    /**
     * Change the visibility of the macron buttons
     * @param isVisible if true, will show the macrons hiding otherwise
     */
    private void __setMacronVisibility(boolean isVisible) {
        this.aButton.setVisible(isVisible);
        this.eButton.setVisible(isVisible);
        this.iButton.setVisible(isVisible);
        this.oButton.setVisible(isVisible);
        this.uButton.setVisible(isVisible);
        this.macronBackground.setVisible(isVisible);
    }

    /**
     * replaces response image with appropriate image based upon status
     */
    private void __setResponseImageView() {
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
    private void __addSubText() {
        Word word = this.game.getWord();
        switch (word.getStatus()) {
        case FAULTED:
            this.message.setText("Hint: Translation is '" + word.getEnglish() + "'");
            break;
        case FAILED:
            String incorrectMsg = incorrectMessages[new Random().nextInt(incorrectMessages.length)];
            this.message.setText(incorrectMsg);

            if (MainApp.getUser().getSelectedAvatar().getSpeechLines() == null)
                return;

            this.avatarMessage.setText(MainApp.getUser().getSelectedAvatar().getSpeechLines()[1]);
            this.speechBubble.setVisible(true);
            break;
        case MASTERED:
            String correctMsg = correctMessages[new Random().nextInt(correctMessages.length)];
            this.message.setText(correctMsg);

            if (MainApp.getUser().getSelectedAvatar().getSpeechLines() == null)
                return;

            this.avatarMessage.setText(MainApp.getUser().getSelectedAvatar().getSpeechLines()[0]);
            this.speechBubble.setVisible(true);
            break;
        default:
            this.message.setText("");
            break;
        }
    }

    /**
     * Show the user the feedback for gaining points
     * @param scoreIncrease the number of points gained, which corresponds to which image will be displayed to the user.
     */
    private void __setScoreIncrease(int scoreIncrease) {
        try {
            this.setImage(scoreIncrease, this.score);
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
            MainApp.getTTS().readWord(this.game.getWord(), repeats, Language.MAORI);
        } catch (Exception e) {
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
            this.setImage(i, progressBar);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load progress bar for " + i + " error " + e);
        }
    }

    /**
     * Update the word index with the current value :)
     */
    private void __updateWordIndexBanner() {
        try {
            this.setImage(this.game.getWordIndex(), wordIndexBanner);
        } catch (FileNotFoundException exception) {
            System.err.println("Unable to load banner for index: " + this.game.getWordIndex());
        }
    }

    /**
     * Set the topic the user is being tested on
     */
    private void setTopicBanner() {
        try {
            this.setImage(this.game.getTopic().getName(), topicBanner);
        } catch (FileNotFoundException exception) {
            System.err.println("Unable to load banner for topic: " + this.game.getTopic().getName());
        }
    }

    //// Button Handlers ////

    /**
     * Handler for the submit button
     */
    public void checkInput(Word input, boolean isInputEmpty) {

        // return if empty textfield
        if (isInputEmpty) {
            __hearWord(1);
            return;
        }

        this.timer.stop();

        InputField.setDisableInputs(false);

        if (this.game.getWord().isEqualStrict(input)) {
            // Correct i.e. MASTERED. Increment score.
            this.game.getWord().setScoreMultiplier(this.timer.getScoreMultiplier());
            Sounds.playSoundEffect("correct");
            this.__updateProgressBar(this.game.getScore());
            this.__setScoreIncrease(this.timer.getScoreMultiplier() * 5);
        }

        this.__toggleLabels();
    }

    /**
     * function to continue from response given for word, determined by status
     */
    public void onEnterContinue() {
        this.__toggleLabels();
        this.timer.start();

        if (this.game.getWord().getStatus() == Status.FAULTED) {
            this.__hearWord(1);
        } else {
            this.__loadNextWord();
        }
    }

    /**
     * Handler for the skip button
     */
    public void skipWordClick() {
        this.game.getWord().setStatus(Status.SKIPPED);
        InputField.setDisableInputs(false);

        this.timer.stop();
        this.__toggleLabels();
    }

    public void pauseClick() {
        Modal.showModal(Modals.PAUSE);
    }

    public void helpClick() {
        Modal.showModal(Modals.HELP);
    }

    public void onEnter(Word input, boolean isInputEmpty) {
        if (this.game.getAwaitingInput()) {
            this.checkInput(input, isInputEmpty);
        } else if (this.continueLabel.isVisible() && !this.continueLabel.isDisabled()) {
            InputField.setDisableInputs(true);
            InputField.reconfigureInputField(this.game.getWord());

            this.onEnterContinue();
        }
    }

    @Override
    protected void start() {
        this.__showElementsForInput(); // no response given initially

        if (this.game.getGameMode() == Gamemode.PRACTICE) {
            this.practiceSign.setVisible(true);
            this.clock.setVisible(false);
            Sounds.playMusic("practice");
        } else {
            this.arc.setVisible(true);
            this.timerText.setVisible(true);
            Sounds.playMusic("game");
        }

        this.__hearWord(1);
        this.timer.start();
        InputField.configureInputField(this.game.getWord(), this.controller, this.submitButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inital setup & loading of data
        super.initialize();

        this.setAvatarImage(avatar);
        this.avatarMessage.setWrapText(true);

        this.game = MainApp.getGameState();
        this.game.setWordIndex(0);
        this.setTopicBanner();
        // Load words from the MainApp
        this.__updateWordIndexBanner();
        // configure timer
        this.timer = new Clock(arc, timerText);
        Modal.setClock(timer);

        controller = this;

        this.settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            super.settingsClick();
        });

        this.pauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            pauseClick();
        });

        this.helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            helpClick();
        });

        this.menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Modal.showGeneralModal(ConfirmModal.INSTANTMENU);
        });

        this.hearButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> __hearWord(1));

        this.skipButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> skipWordClick());

        this.aButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ā"));
        this.eButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ē"));
        this.iButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ī"));
        this.oButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ō"));
        this.uButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> InputField.insertMacron("ū"));
    }
}
