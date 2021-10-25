package com.controllers.modals;

import com.controllers.ModalController;
import com.enums.ErrorModal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This class is the controller for the settings modal.
 */
public class Error extends ModalController {

    @FXML
    Label errorMessage;

    private ErrorModal errorType;

    //// Private Methods ////

    //// Public Methods ////

    @Override
    public void initializeModal() {
        super.initializeModal();
    }

    public void setMessage(String s) {
        this.errorMessage.setText(s);
    }

    public void setErrorType(ErrorModal errorType) {
        this.errorType = errorType;
    }
}
