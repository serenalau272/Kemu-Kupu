package com.se206.g11.screens;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.SystemInterface;
import com.se206.g11.models.SpellingTopic;
import com.se206.g11.models.Word;

import javafx.scene.input.MouseEvent;
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
            MainApp.setRoot("ExitScreen", "Kemu Kupu - Goodbye!", true);
            event.consume();
            // Platform.exit();
        });

        enterTopicSelect_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            MainApp.setRoot("TopicScreen", "Kemu Kupu - Choose a Topic!");
            event.consume();
        });
    }    
}
