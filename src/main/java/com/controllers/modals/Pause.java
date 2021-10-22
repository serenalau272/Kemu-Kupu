package com.controllers.modals;

import com.controllers.ModalController;
import com.enums.ConfirmModal;
import com.util.Modal;

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
    private void replay() {
        Modal.showGeneralModal(ConfirmModal.REPLAY);
    }

    private void showConfirm() {
        Modal.showGeneralModal(ConfirmModal.MENU);
    }

    //// Public Methods ////

    @Override
    public void initializeModal() {
        super.initializeModal();
        this.replayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.replay());
        this.menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.showConfirm());
    }

}
