package com.controllers.views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.models.Game;
import com.util.Sounds;
import com.MainApp;
import com.controllers.ApplicationController;
import com.enums.Gamemode;
import com.enums.Views;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

/**
 * The controller for the gamemode select page.
 */
public class GameMode extends ApplicationController implements Initializable {
    //// Properties ////
    @FXML
    private ImageView practice;
    @FXML
    private ImageView ranked;
    @FXML
    private ImageView practiceAvatar;
    @FXML
    private ImageView rankedAvatar;
    @FXML
    private ImageView backButton;

    //// Private Methods ////

    /**
     * A helper method for converting a double representing saturation into a ColorAdjust value
     * for javafx.
     * @param saturation a double representing the new saturation.
     * @return a ColorAdjust instance which can be applied to a node.
     */
    private ColorAdjust __setSaturation(double saturation) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(saturation);
        return colorAdjust;
    }

    /**
     * Load data for the provided gamemode
     * @param mode an enum represnting which method to load data for
     */
    private void __intialiseMode(Gamemode mode) {
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
                    this.__toggleSaturation(imageViews, true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

            view.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                try {
                    this.__toggleSaturation(imageViews, false);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

            view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    Sounds.playSoundEffect("pop");
                    this.__selectMode(mode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Toggle the saturation of a gamemode option when hovered over.
     * @param imageViews the list of image views to modify
     * @param isBright whether to set this to be saturated (bright) or unsaturated (dark)
     * @throws FileNotFoundException thrown if unable to find the correct file to modify.
     */
    private void __toggleSaturation(List<ImageView> imageViews, boolean isBright) throws FileNotFoundException {
        String modeName = imageViews.get(0).getId();

        if (isBright) {
            ColorAdjust colorAdjust = this.__setSaturation(0);
            imageViews.get(1).setEffect(colorAdjust);
            imageViews.get(1).setOpacity(0.7);
            setImage(modeName + "-bright", imageViews.get(0));
        } else {
            ColorAdjust colorAdjust = this.__setSaturation(-1);
            imageViews.get(1).setEffect(colorAdjust);
            imageViews.get(1).setOpacity(0.3);
            setImage(modeName + "-faded", imageViews.get(0));
        }
    }

    /**
     * A handler for when one of the gamemodes is selected. When this is called we are changing 
     * views to the topic select from gamemode select.
     * @param mode the gamemode that was selected by the user
     * @throws IOException if unable to load the correct file - shouldn't happen in practice.
     */
    private void __selectMode(Gamemode mode) throws IOException {
        Game game = new Game(mode);
        MainApp.setGameState(game);
        MainApp.setRoot(Views.TOPIC);
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        this.__intialiseMode(Gamemode.PRACTICE);
        this.__intialiseMode(Gamemode.RANKED);

        setAvatarImage(rankedAvatar);

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(Views.MENU));
    }
}
