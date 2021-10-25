package com.controllers.modals;

import com.controllers.ModalController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This class is the controller for the settings modal.
 */
public class Error extends ModalController {
    @FXML
    Label errorMessage;

    //// Private Methods ////

    //// Public Methods ////

    @Override
    public void initializeModal() {
        super.initializeModal();
    }

    public void setMessage(String s) {
        this.errorMessage.setText(s);
    }
}
