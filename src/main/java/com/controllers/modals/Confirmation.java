package com.controllers.modals;
import java.io.FileNotFoundException;

import com.MainApp;
import com.controllers.ModalController;
import com.enums.ConfirmModal;
import com.util.Sounds;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Confirmation extends ModalController {

    @FXML ImageView yesButton;
    @FXML Label confirmMessage;

    private ConfirmModal confirmType;

    //// Private Methods ////


    //// Public Methods ////

    @Override
    public void initializeModal() {
        super.initializeModal();
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> confirmType.doConfirmedAction());
    }

    public void setMessage(String s) {
        this.confirmMessage.setText(s);
    }

    public void setConfirmType(ConfirmModal confirmType) {
        this.confirmType = confirmType;
    }
}
