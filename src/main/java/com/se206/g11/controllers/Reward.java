package com.se206.g11.controllers;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.models.Language;
import com.se206.g11.models.View;
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

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        this.game = MainApp.getGameState();

        setStars(this.game.getScore());
        try {
            setImage(this.game.getScore(), score);
        } catch (FileNotFoundException e) {
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
