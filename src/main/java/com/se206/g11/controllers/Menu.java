package com.se206.g11.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.util.Sounds;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * This class is the controller for the menu screen
 */
public class Menu extends ApplicationController implements Initializable {
    @FXML private ImageView exitGame_button;
    @FXML private ImageView enterTopicSelect_button;
    @FXML private ImageView info_button;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();

        //Set event handlers
        //exiting
        exitGame_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Sounds.playSoundEffect("pop");
            MainApp.setRoot("Exit", "Kemu Kupu - Goodbye!");
            event.consume();

            //pause and exit
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> Platform.exit());
            pause.play();
        });

        //open attributions modal
        info_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Sounds.playSoundEffect("pop");
            MainApp.showModal("Attribution", "Kemu Kupu - Asset Attributions");
        });

        //enter topic selection
        enterTopicSelect_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Sounds.playSoundEffect("pop");
            MainApp.setRoot("Topic", "Kemu Kupu - Choose a Topic!");
            event.consume();
        });
    }    
}
