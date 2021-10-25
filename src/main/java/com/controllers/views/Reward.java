package com.controllers.views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.animations.OscillatingComponent;
import com.controllers.ApplicationController;
import com.enums.Achievement;
import com.enums.ErrorModal;
import com.enums.Gamemode;
import com.enums.Views;
import com.util.Modal;
import com.util.Sounds;
import com.models.Game;
import com.models.User;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * This class is the controller for the rewards modal.
 */
public class Reward extends ApplicationController implements Initializable {
    //// Properties ////
    private Game game;
    private User user;
    private Animation avatarAnim;

    // The threshold of score for each star to appear
    private final int[] starThreshold = { 20, 60, 100 };
    private List<Node> stars;
    private int numStars = 0;

    @FXML
    private ImageView againButton;
    @FXML
    private ImageView menuButton;
    @FXML
    private ImageView avatarButton;
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;
    @FXML
    private ImageView star3;
    @FXML
    private ImageView score;
    @FXML
    private ImageView highScore;
    @FXML
    private ImageView newHighScore;

    //// Private (helper) methods ////

    /**
     * Set visibility of stars based on score
     * @param score the score for the game
     */
    private void __setStars(int score) {
        String[] star = { "star" };
        this.stars = findNodesByID(anchorPane, star);

        this.__setStar(0, score);
    }

    /**
     * Unlock a dilligence achievement for the current user.
     * @param bound the level to unlock
     * @param numGames the number of games this user has played
     */
    private void __addStudentAchievement(int bound, int numGames) {
        if (numGames >= bound) {
            String s;
            try {
                s = Achievement.fromString("diligence" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                this.user.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    /**
     * Unlock a highscore achievement for the current user.
     * @param bound the level to unlock
     * @param highscore the score from this game
     */
    private void __addAchieverAchievement(int bound, int highscore) {
        if (highscore >= bound) {
            String s;
            try {
                s = Achievement.fromString("highscore" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                this.user.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    /**
     * Unlock a star achievement for the current user.
     * @param bound the level to unlock
     * @param stars the stars from this game
     */
    private void __addPocketAchievement(int bound, int stars) {
        if (stars >= bound) {
            String s;
            try {
                s = Achievement.fromString("star" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                this.user.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    /**
     * Unlock a speedy achievement for the current user.
     * @param bound the level to unlock
     * @param duration the duration of this game
     */
    private void __addSpeedyAchievement(int bound, int duration) {
        if (bound == duration) {
            String s;
            try {
                s = Achievement.fromString("speedy" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                this.user.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    /**
     * Set the number of stars this user achieved
     * @param index the index of star sto load
     * @param score the users score
     */
    private void __setStar(int index, int score) {
        if (index >= this.stars.size()) {
            try {
                if (MainApp.getGameState().getGameMode() == Gamemode.PRACTICE) {
                    this.user.unlockAchievement("EXPLORER_1");
                    return;
                }
                if (score == 100) {
                    int duration = MainApp.getSetting().getTimerDuration();
                    this.__addSpeedyAchievement(40, duration);
                    this.__addSpeedyAchievement(30, duration);
                    this.__addSpeedyAchievement(15, duration);
                }

                this.user.unlockAchievement("EXPLORER_2");
                this.user.addScore(score, this.numStars);
                int numGamesPlayed = this.user.getNumGamesPlayed();
                this.__addStudentAchievement(5, numGamesPlayed);
                this.__addStudentAchievement(10, numGamesPlayed);
                this.__addStudentAchievement(20, numGamesPlayed);
                this.__addStudentAchievement(50, numGamesPlayed);
                this.__addStudentAchievement(100, numGamesPlayed);
                int highScore = this.user.getHighScore();
                this.__addAchieverAchievement(75, highScore);
                this.__addAchieverAchievement(90, highScore);
                this.__addAchieverAchievement(100, highScore);
                int totalStars = this.user.getTotalStars();
                this.__addPocketAchievement(10, totalStars);
                this.__addPocketAchievement(50, totalStars);
                this.__addPocketAchievement(100, totalStars);
                this.__addPocketAchievement(200, totalStars);
                this.__addPocketAchievement(300, totalStars);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
            return;
        }

        int num = Integer.parseInt(this.stars.get(index).getId().substring(4));
        if (score >= this.starThreshold[num - 1]) {
            Sounds.playSoundEffect("reward");
            this.stars.get(2 - index).setVisible(true);
            this.numStars++;
        }

        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.setOnFinished(e -> {
            this.__setStar(index + 1, score);
        });
        pause.play();
    }

    /**
     * Update the highscore label for this user
     * @param gameScore the score they achieved
     */
    private void __setHighScore(int gameScore) {
        try {
            int prevHighScore = this.user.getHighScore();

            if (gameScore > prevHighScore) {
                // new high score
                this.setImage(gameScore, highScore);
                this.newHighScore.setVisible(true);
            } else {
                this.setImage(prevHighScore, highScore);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load score images");
        }

    }

    //// Public Methods ////

    @Override
    protected void start() {
        this.user = MainApp.getUser();
        int gameScore = this.game.getScore();

        try {
            this.__setStars(gameScore);
            this.setImage(gameScore, this.score);
            this.__setHighScore(gameScore);
        } catch (IOException e) {
            System.err.println(e);
        }

        this.avatarAnim = new OscillatingComponent(this.avatarButton).getAnimator();
        this.avatarAnim.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inital setup & loading of data
        super.initialize();
        this.newHighScore.setVisible(false);
        this.game = MainApp.getGameState();

        this.setAvatarImage(avatarButton);

        // Set event handlers
        this.menuButton.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> {
            MainApp.setRoot(Views.MENU);
            this.avatarAnim.stop();
        });

        this.againButton.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> {
            MainApp.setRoot(Views.GAMEMODE);
            this.avatarAnim.stop();
        });

        this.avatarButton.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> {
            int num = new Random().nextInt(3);
            switch (num) {
            case 0:
                Sounds.playSoundEffect("bee-utiful");
                break;
            case 1:
                Sounds.playSoundEffect("kapai");
                break;
            default:
                Sounds.playSoundEffect("bzz-ness");
                break;
            }
        });
    }
}
