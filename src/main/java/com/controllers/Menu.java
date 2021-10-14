package com.controllers;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.ApplicationController;
import com.MainApp;
import com.enums.Modals;
import com.enums.View;
import com.util.Sounds;

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
    @FXML private ImageView exitGameButton;
    @FXML private ImageView enterTopicSelectButton;
    @FXML private ImageView settingsButton;
    @FXML private ImageView profileButton;
    @FXML private ImageView helpButton;
    @FXML private ImageView settingsLabel;
    @FXML private ImageView profileLabel;
    @FXML private ImageView helpLabel;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();

        Sounds.playMusic("menu");

        //Set event handlers
        //exiting
        exitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Sounds.playSoundEffect("pop");
            MainApp.setRoot(View.EXIT);
            event.consume();

            //pause and exit
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> Platform.exit());
            pause.play();
        });


        String[] iconButtons = {"settingsButton", "profileButton", "helpButton"};
        List<Node> icons = findNodesByID(anchorPane, iconButtons);
        icons.forEach(i -> {
            i.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                String labelId = i.getId().replace("Button", "Label");
                findNodesByID(anchorPane, labelId).setVisible(true);
            });
            i.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                String labelId = i.getId().replace("Button", "Label");
                findNodesByID(anchorPane, labelId).setVisible(false);
            });
        });

        settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            super.settingsClick();
        });

        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            MainApp.showModal(Modals.PROFILE);
        });

        helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Sounds.playSoundEffect("pop");
            MainApp.showModal(Modals.HELP);
        });

        //open attributions modal
        // infoButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        //     Sounds.playSoundEffect("pop");
        //     MainApp.showModal(Modals.ATTRIBUTION);
        // });

        //enter topic selection
        enterTopicSelectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Sounds.playSoundEffect("pop");
            MainApp.setRoot(View.GAMEMODE);
            event.consume();
        });
    }    
}
