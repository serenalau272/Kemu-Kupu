package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This class is the controller for the settings modal.
 */
public class AttributionsScreenController extends ApplicationController implements Initializable {
    @FXML ImageView exit_button;

    /**
     * Close this modal
     */
    private void __close() {
        ((Stage) this.anchorPane.getScene().getWindow()).close();
    }
    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        
        //Set event handlers
        this.exit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.__close());  
    }
}
