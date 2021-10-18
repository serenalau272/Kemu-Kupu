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

    private void onAnimateOut(Duration dur, double delta) {
        animateImage(dur, delta, 0);
    }

    private void animateImage(Duration dur, double delta, int index) {
        Animation anim;

        if (animated[index].getId().equals("exitGameButton")) {
            anim = new SlideComponentHorizontal(animated[index], dur, delta).getAnimator();
            Animation anim2 = new SlideComponentHorizontal(exitMessage, dur, delta).getAnimator();
            anim2.play();
        } else {
            anim = new SlideComponentHorizontal(animated[index], dur, delta * -1).getAnimator();
        }

        if (index + 1 >= animated.length) {
            anim.setOnFinished(e -> transition());
        } else {
            anim.setOnFinished(e -> animateImage(dur, delta, index + 1));
        }

        anim.play();
    }

    private void transition() {
        menuAnimation.stop();
        MainApp.setRoot(Views.GAMEMODE);
        Sounds.playSoundEffect("pop");
    }

    @Override
    protected void start() {
        animated[0] = exitGameButton;
        animated[3] = helpButton;
        animated[1] = settingsButton;
        animated[2] = profileButton;

        menuAnimation = new OscillatingComponent(playButton).getAnimator();
        menuAnimation.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inital setup & loading of data
        super.initialize();

        Sounds.playMusic("menu");

        // Set event handlers
        // exiting
        exitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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

        settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            super.settingsClick();
        });

        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            if (MainApp.getUser().getUsername() == null){
                //guest
                MainApp.setRoot(Views.SIGNIN);
            } else {
                MainApp.setRoot(Views.PROFILE);
            }            
        });

        helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Modal.showModal(Modals.HELP);
        });

        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            onAnimateOut(Duration.millis(100), 120);
            event.consume();
        });
        // open attributions modal
        // infoButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        // Sounds.playSoundEffect("pop");
        // MainApp.showModal(Modals.ATTRIBUTION);
        // });
        
    }    
}
