package com.se206.g11.screens;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.SystemInterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RewardsScreenController extends ApplicationController implements Initializable {
    private int scoreNum;
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
     * @param fxml the name of the file to open
     * @param title the title of the window to set
     */
    private void __changeClose(String fxml, String title) {
        MainApp.closeModal();
        MainApp.setRoot(fxml, title);
        hideStars();
        

    }

    private void setStars(int score) {
        if (score >= this.starThreshold[0]) {
            star1.setVisible(true);
            if (score >= this.starThreshold[1]) {
                star2.setVisible(true);
                if (score >= this.starThreshold[2]) {
                    star3.setVisible(true);
                }
            }
        }
    }

    private void hideStars() {
        star1.setVisible(false);
        star2.setVisible(false);
        star3.setVisible(false);
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        this.scoreNum = MainApp.getScore();
        setStars(this.scoreNum);
        try {
            setImage(this.scoreNum, score);
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
        
        //if (score >= this.starThreshold[i]) this.anchorPane.lookup("star" + i).setVisible(true); 
        menu_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> __changeClose("MenuScreen", "Kemu Kupu"));
        again_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> __changeClose("TopicScreen", "Kemu Kupu - Choose a Topic!"));
        pot_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> {
            SystemInterface.readWord("Ka Pai");
        });
    }    
}
