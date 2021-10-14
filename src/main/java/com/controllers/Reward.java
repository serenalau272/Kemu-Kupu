package com.controllers;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.ApplicationController;
import com.MainApp;
import com.enums.Language;
import com.enums.View;
import com.models.Word;
import com.models.Game;

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

    private void setHighScore(int gameScore) throws IOException {
        File userStats = new File("./.user/.userStats.txt");
        int prevHighScore = Game.getHighScore();
        if (gameScore > prevHighScore) {
            BufferedWriter statsWriter = new BufferedWriter(new FileWriter(userStats, false));
            statsWriter.write(String.valueOf(gameScore));
            setImage(gameScore, highScore);
            new_label.setVisible(true);
            statsWriter.close();
        } else {
            setImage(prevHighScore, highScore);
        }
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
        menu_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> MainApp.setRoot(View.MENU));
        again_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> MainApp.setRoot(View.GAMEMODE));
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
