package com.controllers.views;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    //// Private Methods ////

    private void intialiseMode(Gamemode mode) {
        List<ImageView> imageViews = new ArrayList<ImageView>();
        
        switch (mode) {
            case PRACTICE:
                imageViews.add(practice);
                imageViews.add(practiceAvatar);
                break;
            case RANKED:
                imageViews.add(ranked);
                imageViews.add(rankedAvatar);
                break;
            default:
                System.err.println("ERROR: Game mode not implemented.");
                break;
        }

        for (ImageView view : imageViews) {
            view.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                try {
                    toggleSaturation(imageViews, true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

            view.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                try {
                    toggleSaturation(imageViews, false);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

            view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    Sounds.playSoundEffect("pop");
                    selectMode(mode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void toggleSaturation(List<ImageView> imageViews, boolean isBright) throws FileNotFoundException{
        String modeName = imageViews.get(0).getId();

        if (isBright) {
            ColorAdjust colorAdjust = setSaturation(0);
            imageViews.get(1).setEffect(colorAdjust);
            imageViews.get(1).setOpacity(0.7);
            setImage(modeName + "-bright", imageViews.get(0));
        } else {
            ColorAdjust colorAdjust = setSaturation(-1);
            imageViews.get(1).setEffect(colorAdjust);
            imageViews.get(1).setOpacity(0.3);
            setImage(modeName + "-faded", imageViews.get(0));
        }
    }

    private void selectMode(Gamemode mode) throws IOException {
        Game game = new Game(mode);
        if (mode == Gamemode.PRACTICE){
            MainApp.getUser().unlockAchievement("EXPLORER_1");
        } else {
            MainApp.getUser().unlockAchievement("EXPLORER_2");
        }
        MainApp.setGameState(game);
        MainApp.setRoot(View.TOPIC);
    }

    private ColorAdjust setSaturation(double saturation) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(saturation);
        return colorAdjust;
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        intialiseMode(Gamemode.PRACTICE);
        intialiseMode(Gamemode.RANKED);

        setAvatarImage(rankedAvatar);

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.MENU));
    }
}
