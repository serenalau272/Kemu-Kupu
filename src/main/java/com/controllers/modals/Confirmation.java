package com.controllers.modals;

import com.controllers.ModalController;
import com.enums.ConfirmModal;
import com.util.Modal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the confirmation modal.
 */
public class Confirmation extends ModalController {
    @FXML
    ImageView yesButton;
    @FXML
    ImageView exitButton;
    @FXML
    Label confirmMessage;

    private ConfirmModal confirmType;

    //// Private Methods ////

    //// Public Methods ////

    @Override
    public void initializeModal() {
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> confirmType.doAction());
    }

    /**
     * method to set the message of the confirmation label
     * 
     * @param s
     */
    public void setMessage(String s) {
        this.confirmMessage.setText(s);
    }

    /**
     * method to set the type of the confirmation
     * 
     * @param confirmType
     */
    public void setConfirmType(ConfirmModal confirmType) {
        this.confirmType = confirmType;

        //overrides the exitButton eventHandler to resume the quiz timer on closing when of enum ConfirmModal.INSTANTMENU
        if (confirmType == ConfirmModal.INSTANTMENU) {
            this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> Modal.closeModal(true));
        } else {
            this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> Modal.closeModal(false));
        }
    }
}
