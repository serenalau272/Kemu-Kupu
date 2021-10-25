package com.controllers.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.animations.OscillatingComponent;
import com.components.animations.SlideComponentHorizontal;
import com.controllers.ApplicationController;
import com.enums.Modals;
import com.enums.Views;
import com.util.Modal;
import com.util.Sounds;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * This class is the controller for the menu screen
 */
public class Menu extends ApplicationController implements Initializable {

    //// Properties ////

    @FXML
    private ImageView exitGameButton;
    @FXML
    private ImageView playButton;
    @FXML
    private ImageView settingsButton;
    @FXML
    private ImageView profileButton;
    @FXML
    private ImageView helpButton;
    @FXML
    private ImageView settingsLabel;
    @FXML
    private ImageView profileLabel;
    @FXML
    private ImageView helpLabel;
    @FXML
    private ImageView exitMessage;

    private Animation menuAnimation;
    private ImageView[] animated = new ImageView[4];

    //// Private (helper) Methods ////

    /**
     * Animate the images on the homescreen to slide them horizontally.
     * @param dur duration to slide
     * @param delta total offset of the images inital location (how far to move it)
     * @param index the index of the image to move
     */
    private void __animateImage(Duration dur, double delta, int index) {
        Animation anim;

        if (this.animated[index].getId().equals("exitGameButton")) {
            anim = new SlideComponentHorizontal(this.animated[index], dur, delta).getAnimator();
            Animation anim2 = new SlideComponentHorizontal(exitMessage, dur, delta).getAnimator();
            anim2.play();
        } else {
            anim = new SlideComponentHorizontal(this.animated[index], dur, delta * -1).getAnimator();
        }

        if (index + 1 >= this.animated.length) {
            anim.setOnFinished(e -> this.__transition());
        } else {
            anim.setOnFinished(e -> this.__animateImage(dur, delta, index + 1));
        }

        anim.play();
    }

    /**
     * A proxy method to animate an image.
     * @param dur duration to move the image.
     * @param delta how far to move the image.
     */
    private void __onAnimateOut(Duration dur, double delta) {
        this.__animateImage(dur, delta, 0);
    }

    /**
     * Create an animation between the current view and a new view.
     */
    private void __transition() {
        this.menuAnimation.stop();
        MainApp.setRoot(Views.GAMEMODE);
    }
    
    @Override
    protected void start() {
        this.animated[0] = exitGameButton;
        this.animated[3] = helpButton;
        this.animated[1] = settingsButton;
        this.animated[2] = profileButton;

        this.menuAnimation = new OscillatingComponent(playButton).getAnimator();
        this.menuAnimation.play();
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inital setup & loading of data
        super.initialize();

        Sounds.playMusic("menu");

        // Set event handlers
        // exiting
        this.exitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            MainApp.setRoot(Views.EXIT);
            event.consume();

            // pause and exit
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> Platform.exit());
            pause.play();
        });

        String[] iconButtons = { "settingsButton", "profileButton", "helpButton" };
        List<Node> icons = findNodesByID(anchorPane, iconButtons);
        icons.forEach(i -> {
            i.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                String labelId = i.getId().replace("Button", "Label");
                findNodeByID(anchorPane, labelId).setVisible(true);
            });
            i.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                String labelId = i.getId().replace("Button", "Label");
                findNodeByID(anchorPane, labelId).setVisible(false);
            });
        });

        this.settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            super.settingsClick();
        });

        this.profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            if (MainApp.getUser() == null) {
                MainApp.setRoot(Views.SIGNIN);
            } else if (MainApp.getUser().getUsername() == null) {
                MainApp.setRoot(Views.SIGNIN);
            } else {
                MainApp.setRoot(Views.PROFILE);
            }
        });

        this.helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Modal.showModal(Modals.HELP);
        });

        this.playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.__onAnimateOut(Duration.millis(100), 120);
            event.consume();
        });
    }
}
