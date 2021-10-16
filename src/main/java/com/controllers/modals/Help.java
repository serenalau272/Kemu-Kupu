package com.controllers.modals;
import java.net.URL;
import java.util.ResourceBundle;

import com.App;
import com.controllers.ApplicationController;
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
        App.closeModal();
    }

    private void openAttributions() {
        App.showModal(Modals.ATTRIBUTION);
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
