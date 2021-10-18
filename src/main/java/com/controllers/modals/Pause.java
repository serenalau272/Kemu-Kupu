package com.controllers.modals;

import com.MainApp;
import com.controllers.ModalController;
import com.enums.View;
import com.util.Modal;
import com.util.Sounds;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Pause extends ModalController {
    //The users current settings Selection

    @FXML ImageView menuButton;
    @FXML ImageView replayButton;

    //// Private Methods ////


    /**
     * Change the mainapp to a new window, and close this modal
     * @param v the view to switch to
     */
    private void changeClose(View v) {
        Modal.closeModal();
        MainApp.setRoot(v);
        if (v == View.GAMEMODE){
            Sounds.playMusic("menu");
        }
    }


    //// Public Methods ////

    @Override
    public void initializeModal() {
        super.initializeModal();
        this.replayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.changeClose(View.GAMEMODE));
        this.menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.changeClose(View.MENU));
    }
}
