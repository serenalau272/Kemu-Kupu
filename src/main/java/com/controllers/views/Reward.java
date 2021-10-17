package com.controllers.views;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.MainApp;
import com.controllers.ApplicationController;
import com.enums.Language;
import com.enums.View;
import com.models.Word;
import com.util.Sounds;
import com.models.Game;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * This class is the controller for the rewards modal.
 */
public class Reward extends ApplicationController implements Initializable {
    private Game game;
    //The threshold of score for each star to appear
    private final int[] starThreshold = {20, 60, 100};
    private List<Node> stars;

    @FXML private ImageView againButton;
    @FXML private ImageView menuButton;
    @FXML private ImageView potButton;
    @FXML private ImageView star1;
    @FXML private ImageView star2;
    @FXML private ImageView star3;
    @FXML private ImageView score;
    @FXML private ImageView highScore;
    @FXML private ImageView newLabel;

    //// Private (helper) methods ////

    /**
     * Set visibility of stars based on score
     * @param score the score for the game
     */
    private void setStars(int score) {
        String[] star = {"star"};
        stars = findNodesByID(anchorPane, star);

        setStar(0, score);
    }

    private void setStar(int index, int score){
        if (index >= stars.size()) return;

        int num = Integer.parseInt(stars.get(index).getId().substring(4));
        if (score >= this.starThreshold[num-1]) {
            Sounds.playSoundEffect("correct");
            stars.get(index).setVisible(true);
        }

        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.setOnFinished(e -> {
            setStar(index + 1, score);
        });
        pause.play();
    }

    private void setHighScore(int gameScore) throws IOException {
        File userStats = new File("./.user/.userStats.txt");
        int prevHighScore = Game.getHighScore();
        if (gameScore > prevHighScore) {
            BufferedWriter statsWriter = new BufferedWriter(new FileWriter(userStats, false));
            statsWriter.write(String.valueOf(gameScore));
            setImage(gameScore, highScore);
            newLabel.setVisible(true);
            statsWriter.close();
        } else {
            setImage(prevHighScore, highScore);
        }
    }

    //// Public Methods ////

    @Override
    protected void start() {
        int gameScore = this.game.getScore();

        try {
            setStars(gameScore);
            setHighScore(gameScore);
            setImage(gameScore, score);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        newLabel.setVisible(false);
        this.game = MainApp.getGameState();
        
        //Set event handlers
        menuButton.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> MainApp.setRoot(View.MENU));
        againButton.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> MainApp.setRoot(View.GAMEMODE));
        potButton.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> {
            try {
                MainApp.getTTS().readWord(new Word("Ka Pai", null), 1, Language.MAORI);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }    
}
