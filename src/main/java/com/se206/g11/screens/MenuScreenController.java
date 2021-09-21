package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;

import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class MenuScreenController extends ApplicationController implements Initializable {
    @FXML
    private ImageView exitGame;
    @FXML
    private ImageView enterTopicSelect;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exitGame.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            MainApp.setRoot("ExitScreen", "Kemu Kupu - Goodbye!", true);
            event.consume();
            Platform.exit();
        });

        enterTopicSelect.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            MainApp.setRoot("TopicScreen", "Kemu Kupu - Choose a Topic!");
            event.consume();
        });
    }    
}
