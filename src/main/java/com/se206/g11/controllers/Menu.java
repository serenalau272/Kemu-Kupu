package com.se206.g11.controllers;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.models.Modals;
import com.se206.g11.models.View;
import com.se206.g11.util.Sounds;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * This class is the controller for the menu screen
 */
public class Menu extends ApplicationController implements Initializable {
    @FXML private ImageView exitGame_button;
    @FXML private ImageView enterTopicSelect_button;
    @FXML private ImageView settings_button;
    @FXML private ImageView profile_button;
    @FXML private ImageView help_button;
    @FXML private ImageView settings_label;
    @FXML private ImageView profile_label;
    @FXML private ImageView help_label;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();

        //Set event handlers
        //exiting
        exitGame_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Sounds.playSoundEffect("pop");
            MainApp.setRoot(View.EXIT);
            event.consume();

            //pause and exit
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> Platform.exit());
            pause.play();
        });


        String[] iconButtons = {"settings_button", "profile_button", "help_button"};
        List<Node> icons = findNodesByID(anchorPane, iconButtons);
        icons.forEach(i -> {
            i.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                String labelId = i.getId().replace("_button", "_label");
                findNodesByID(anchorPane, labelId).setVisible(true);
            });
            i.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                String labelId = i.getId().replace("_button", "_label");
                findNodesByID(anchorPane, labelId).setVisible(false);
            });
        });

        //open attributions modal
        // info_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        //     Sounds.playSoundEffect("pop");
        //     MainApp.showModal(Modals.ATTRIBUTION);
        // });

        //enter topic selection
        enterTopicSelect_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Sounds.playSoundEffect("pop");
            MainApp.setRoot(View.TOPIC);
            event.consume();
        });
    }    
}
