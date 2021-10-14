package com.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.ApplicationController;
import com.MainApp;
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

    @FXML ImageView menu_button;
    @FXML ImageView resume_button;
    @FXML ImageView replay_button;

    //// Private Methods ////

    
    private void resume() {
        MainApp.closeModal();
    }

    /**
     * Change the mainapp to a new window, and close this modal
     * @param v the view to switch to
     */
    private void changeClose(View v) {
        MainApp.closeModal();
        MainApp.setRoot(v);
    }


    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        //Set event handlers
        this.resume_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.resume());  
        this.replay_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.changeClose(View.GAMEMODE));
        this.menu_button.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> this.changeClose(View.MENU));
    }
}
