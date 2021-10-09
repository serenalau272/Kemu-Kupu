package com.se206.g11.controllers;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.enums.Language;
import com.se206.g11.enums.View;
import com.se206.g11.models.Word;
import com.se206.g11.models.Game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the rewards modal.
 */
public class Reward extends ApplicationController implements Initializable {
    private Game game;
    //The threshold of score for each star to appear
    private final int[] starThreshold = {20, 60, 100};

    @FXML private ImageView again_button;
    @FXML private ImageView menu_button;
    @FXML private ImageView pot_button;
    @FXML private ImageView star1;
    @FXML private ImageView star2;
    @FXML private ImageView star3;
    @FXML private ImageView score;
    @FXML private ImageView highScore;
    @FXML private ImageView new_label;

    //// Private (helper) methods ////
    /**
     * Change the mainapp to a new window, and close this modal
     * @param v the view to switch to
     */
    private void __changeClose(View v) {
        MainApp.closeModal();
        MainApp.setRoot(v);
        hideStars();
    }

    /**
     * Set visibility of stars based on score
     * @param score the score for the game
     */
    private void setStars(int score) {
        String[] star = {"star"};
        List<Node> stars = findNodesByID(anchorPane, star);
        for (Node s : stars) {
            int num = Integer.parseInt(s.getId().substring(4));
            if (score >= this.starThreshold[num-1]) {
                s.setVisible(true);
            }
        }
    }

    /**
     * Hide all stars
     */
    private void hideStars() {
        star1.setVisible(false);
        star2.setVisible(false);
        star3.setVisible(false);
    }

    private Integer getHighScore() throws IOException {
        File userStats = new File(".userStats");
        BufferedReader statsReader = new BufferedReader(new FileReader(userStats));
        Integer highScore = 0;
        String line;
        if ((line = statsReader.readLine()) != null) {
            highScore = Integer.parseInt(line);
        }
        statsReader.close();
        return highScore;
    }

    private void setHighScore(int gameScore) throws IOException {
        File userStats = new File(".userStats");
        BufferedWriter statsWriter = new BufferedWriter(new FileWriter(userStats, false));
        int prevHighScore = getHighScore();
        if (gameScore > prevHighScore) {
            statsWriter.write(gameScore);
            setImage(gameScore, highScore);
            new_label.setVisible(true);
        } else {
            setImage(prevHighScore, highScore);
        }
        statsWriter.close();
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        new_label.setVisible(false);
        this.game = MainApp.getGameState();
        int gameScore = this.game.getScore();

        setStars(gameScore);

        try {
            setHighScore(gameScore);
            setImage(gameScore, score);
        } catch (IOException e) {
            System.err.println(e);
        }
        
        //Set event handlers
        menu_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> __changeClose(View.MENU));
        again_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> __changeClose(View.TOPIC));
        pot_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> {
            try {
                MainApp.tts.readWord(new Word("Ka Pai", null), 1, Language.MAORI);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }    
}
