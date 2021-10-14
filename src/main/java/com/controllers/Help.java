package com.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.ApplicationController;
import com.MainApp;
import com.enums.Modals;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Help extends ApplicationController implements Initializable {

    @FXML ImageView attributionsButton;
    @FXML ImageView exitButton;

    //// Private Methods ////

    /**
     * Close this modal
     */
    private void __close() {
        MainApp.closeModal();
    }

    private void openAttributions() {
        MainApp.showModal(Modals.ATTRIBUTION);
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        
        //Set event handlers
        this.attributionsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.openAttributions());
        this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__close());  
        
    }
}
