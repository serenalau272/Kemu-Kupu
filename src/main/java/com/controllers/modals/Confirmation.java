package com.controllers.modals;

import com.controllers.ModalController;
import com.enums.ConfirmModal;
import com.util.Modal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Confirmation extends ModalController {

    @FXML ImageView yesButton;
    @FXML ImageView exitButton;
    @FXML Label confirmMessage;

    private ConfirmModal confirmType;

    //// Private Methods ////


    //// Public Methods ////

    @Override
    public void initializeModal() {
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> confirmType.doAction());
    }

    public void setMessage(String s) {
        this.confirmMessage.setText(s);
    }

    public void setConfirmType(ConfirmModal confirmType) {
        this.confirmType = confirmType;
        if (confirmType == ConfirmModal.INSTANTMENU) {
            this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> Modal.closeModal(true)); 
            System.out.println("setting true!");
        } else {
            this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> Modal.closeModal(false)); 
        }
    }
}
