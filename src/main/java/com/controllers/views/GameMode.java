package com.controllers.views;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.models.Game;
import com.models.User;
import com.util.Sounds;
import com.MainApp;
import com.controllers.ApplicationController;
import com.enums.Gamemode;
import com.enums.View;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;


public class GameMode extends ApplicationController implements Initializable {
    User currentUser = MainApp.getUser();

    @FXML private ImageView practice;
    @FXML private ImageView ranked;
    @FXML private ImageView practiceAvatar;    
    @FXML private ImageView rankedAvatar;
    @FXML private ImageView backButton;

    private Boolean isAvatar;

    //// Private Methods ////

    private void intialiseMode(ImageView mode) {
        this.isAvatar = mode.getId().contains("Avatar");
        
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
        if (isAvatar) {
            if (isBright) {
                ColorAdjust colorAdjust = setSaturation(1);
                view.setEffect(colorAdjust);
                view.setOpacity(0.7);
            } else {
                ColorAdjust colorAdjust = setSaturation(0);
                view.setEffect(colorAdjust);
                view.setOpacity(0.3);
            }
        } else {
            String mode = view.getId();
            if (isBright) {
                setImage(mode + "-bright", view);
            } else {
                setImage(mode + "-faded", view);
            }
        }
    }

    private void selectMode(String mode) throws IOException {
        Gamemode modeEnum = Gamemode.RANKED;
        switch (mode) {
            case "practice": 
                modeEnum = Gamemode.PRACTICE;
                currentUser.unlockAchievement("EXPLORER_1");
                break;
            case "ranked":
                modeEnum = Gamemode.RANKED;
                currentUser.unlockAchievement("EXPLORER_2");
                break;
            default: 
                System.err.println("Mode not valid.");
        }

        Game game = new Game(modeEnum);
        MainApp.setGameState(game);
        MainApp.setRoot(View.TOPIC);
    }

    private ColorAdjust setSaturation(int saturation) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(saturation);
        return colorAdjust;
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        intialiseMode(practice);
        intialiseMode(practiceAvatar);
        intialiseMode(ranked);
        intialiseMode(rankedAvatar);

        setAvatarImage(rankedAvatar);

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.MENU));
    }
}
