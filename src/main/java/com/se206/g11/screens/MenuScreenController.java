package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;

import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class MenuScreenController extends ApplicationController implements Initializable {
    @FXML
    private ImageView exitGame;
    @FXML
    private ImageView enterGameMode;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exitGame.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Exiting Game!");
            transitScene("exit", event);
            // event.consume(); Perhaps Thread.sleep()
            Platform.exit();
        });
        enterGameMode.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Entering Topic!");
            transitScene("topic", event);
            event.consume();
        });
    }    
}
