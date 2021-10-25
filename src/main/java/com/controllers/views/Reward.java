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
    private ImageView newLabel;

    //// Private (helper) methods ////

    /**
     * Set visibility of stars based on score
     * 
     * @param score the score for the game
     */
    private void setStars(int score) {
        String[] star = { "star" };
        stars = findNodesByID(anchorPane, star);

        setStar(0, score);
    }

    private void addStudentAchievement(int bound, int numGames) {
        if (numGames >= bound) {
            String s;
            try {
                s = Achievement.fromString("diligence" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                user.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    private void addAchieverAchievement(int bound, int highscore) {
        if (highscore >= bound) {
            String s;
            try {
                s = Achievement.fromString("highscore" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                user.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    private void addPocketAchievement(int bound, int stars) {
        if (stars >= bound) {
            String s;
            try {
                s = Achievement.fromString("star" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                user.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    private void addSpeedyAchievement(int bound, int duration) {
        if (bound == duration) {
            String s;
            try {
                s = Achievement.fromString("speedy" + Integer.toString(bound));
            } catch (LoadException e) {
                System.err.println("String cannot be mapped into an achievement");
                return;
            }
            try {
                user.unlockAchievement(s);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }
    }

    private void setStar(int index, int score) {
        if (index >= stars.size()) {
            try {
                if (MainApp.getGameState().getGameMode() == Gamemode.PRACTICE) {
                    user.unlockAchievement("EXPLORER_1");
                    return;
                }
                if (score == 100) {
                    int duration = MainApp.getSetting().getTimerDuration();
                    addSpeedyAchievement(40, duration);
                    addSpeedyAchievement(30, duration);
                    addSpeedyAchievement(15, duration);
                }

                user.unlockAchievement("EXPLORER_2");
                user.addScore(score, numStars);
                int numGamesPlayed = user.getNumGamesPlayed();
                addStudentAchievement(5, numGamesPlayed);
                addStudentAchievement(10, numGamesPlayed);
                addStudentAchievement(20, numGamesPlayed);
                addStudentAchievement(50, numGamesPlayed);
                addStudentAchievement(100, numGamesPlayed);
                int highScore = user.getHighScore();
                addAchieverAchievement(75, highScore);
                addAchieverAchievement(90, highScore);
                addAchieverAchievement(100, highScore);
                int totalStars = user.getTotalStars();
                addPocketAchievement(10, totalStars);
                addPocketAchievement(50, totalStars);
                addPocketAchievement(100, totalStars);
                addPocketAchievement(200, totalStars);
                addPocketAchievement(300, totalStars);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
            return;
        }

        int num = Integer.parseInt(stars.get(index).getId().substring(4));
        if (score >= this.starThreshold[num - 1]) {
            Sounds.playSoundEffect("reward");
            stars.get(2 - index).setVisible(true);
            numStars++;
        }

        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.setOnFinished(e -> {
            setStar(index + 1, score);
        });
        pause.play();
    }

    private void setHighScore(int gameScore) {
        try {
            int prevHighScore = user.getHighScore();

            if (gameScore > prevHighScore) {
                // new high score
                setImage(gameScore, highScore);
                newLabel.setVisible(true);
            } else {
                setImage(prevHighScore, highScore);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load score images");
        }

    }

    //// Public Methods ////

    @Override
    protected void start() {
        user = MainApp.getUser();
        int gameScore = this.game.getScore();

        try {
            setStars(gameScore);
            setImage(gameScore, score);
            setHighScore(gameScore);
        } catch (IOException e) {
            System.err.println(e);
        }

        avatarAnim = new OscillatingComponent(avatarButton).getAnimator();
        avatarAnim.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inital setup & loading of data
        super.initialize();
        newLabel.setVisible(false);
        this.game = MainApp.getGameState();

        setAvatarImage(avatarButton);

        // Set event handlers
        menuButton.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> {
            MainApp.setRoot(Views.MENU);
            avatarAnim.stop();
        });

        againButton.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> {
            MainApp.setRoot(Views.GAMEMODE);
            avatarAnim.stop();
        });

        avatarButton.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> {
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
