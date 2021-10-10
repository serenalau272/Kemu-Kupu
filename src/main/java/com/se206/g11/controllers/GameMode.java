package com.se206.g11.controllers;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.models.Game;
import com.se206.g11.util.Sounds;
import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.enums.Gamemode;
import com.se206.g11.enums.View;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;


public class GameMode extends ApplicationController implements Initializable {


    @FXML private ImageView practice;
    @FXML private ImageView ranked;

    //// Private Methods ////

    private void intialiseMode(ImageView mode) {
        mode.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            try {
                toggleSaturation(mode, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mode.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            try {
                toggleSaturation(mode, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mode.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                Sounds.playSoundEffect("pop");
                selectMode(mode.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void toggleSaturation(ImageView view, boolean isBright) throws FileNotFoundException{
        String mode = view.getId();
        if (isBright) {
            setImage(mode + "-bright", view);
        } else {
            setImage(mode + "-faded", view);
        }
    }

    private void selectMode(String mode) throws IOException {
        Gamemode modeEnum = Gamemode.RANKED;
        switch (mode) {
            case "practice": 
                modeEnum = Gamemode.PRACTICE;
                break;
            case "ranked":
                modeEnum = Gamemode.RANKED;
                break;
            default: 
                System.err.println("Mode not valid.");
        }

        Game game = new Game(modeEnum);
        MainApp.setGameState(game);
        MainApp.setRoot(View.TOPIC);
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Sounds.playMusic("menu");
        intialiseMode(practice);
        intialiseMode(ranked);
    }
}
