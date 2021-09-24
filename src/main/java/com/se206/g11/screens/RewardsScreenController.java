package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RewardsScreenController extends ApplicationController implements Initializable {
    private int score;
    //The threshold of score for each star to appear
    private final int[] starThreshold = {1, 3, 5};

    @FXML private ImageView again_button;
    @FXML private ImageView menu_button;

    //// Private (helper) methods ////
    /**
     * Change the mainapp to a new window, and close this modal
     * @param fxml the name of the file to open
     * @param title the title of the window to set
     */
    private void __changeClose(String fxml, String title) {
        MainApp.setRoot(fxml, title);
        ((Stage) this.anchorPane.getScene().getWindow()).close();
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        this.score = MainApp.getScore();
        for (int i = 0; i < 3; i++) {
            if (score >= this.starThreshold[i]) this.anchorPane.lookup("star" + i).setVisible(true); 
        }
        menu_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> __changeClose("MenuScreen", "Kemu Kupu"));
        again_button.addEventHandler(MouseEvent.MOUSE_RELEASED, _e -> __changeClose("TopicScreen", "Kemu Kupu - Choose a Topic!"));
    }    
}
