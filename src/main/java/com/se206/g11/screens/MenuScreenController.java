package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.SystemInterface;
import com.se206.g11.models.Language;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class MenuScreenController extends ApplicationController implements Initializable {
    @FXML
    private ImageView exitGame_button;
    @FXML
    private ImageView enterTopicSelect_button;    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();

        exitGame_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //MainApp.showModal("RewardScreen", "title");

            MainApp.setRoot("ExitScreen", "Kemu Kupu - Goodbye!");
            event.consume();
            SystemInterface.readWord("Thanks for playing.", Language.ENGLISH);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> Platform.exit());
            pause.play();
        });

        enterTopicSelect_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            MainApp.setRoot("TopicScreen", "Kemu Kupu - Choose a Topic!");
            event.consume();
        });
    }    
}
