package com.controllers.modals;
import java.net.URL;
import java.util.ResourceBundle;

import com.App;
import com.controllers.ApplicationController;
import com.enums.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Pause extends ApplicationController implements Initializable {
    //The users current settings Selection

    @FXML ImageView menuButton;
    @FXML ImageView resumeButton;
    @FXML ImageView replayButton;

    //// Private Methods ////

    
    private void resume() {
        App.closeModal();
    }

    /**
     * Change the mainapp to a new window, and close this modal
     * @param v the view to switch to
     */
    private void changeClose(View v) {
        App.closeModal();
        App.setRoot(v);
    }


    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        //Set event handlers
        this.resumeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.resume());  
        this.replayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.changeClose(View.GAMEMODE));
        this.menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.changeClose(View.MENU));
    }
}
