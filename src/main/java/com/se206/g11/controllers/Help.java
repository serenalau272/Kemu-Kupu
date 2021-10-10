package com.se206.g11.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.enums.Modals;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Help extends ApplicationController implements Initializable {

    @FXML ImageView attributions_button;
    @FXML ImageView exit_button;

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
        this.attributions_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.openAttributions());
        this.exit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__close());  
        
    }
}
