package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;

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
            MainApp.showModal("SettingScreen", "testing");
            // MainApp.setRoot("ExitScreen", "Kemu Kupu - Goodbye!");
            // event.consume();
            // Platform.exit();
        });

        enterTopicSelect_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            MainApp.setRoot("TopicScreen", "Kemu Kupu - Choose a Topic!");
            event.consume();
        });
    }    
}
