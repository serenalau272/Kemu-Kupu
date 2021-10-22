package com.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.util.Modal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the base controller for all modals
 */
public class ModalController extends ApplicationController implements Initializable {
    

    @FXML ImageView exitButton;


    //// Public Methods ////

    public void initializeModal(){
        this.exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> Modal.closeModal(false)); 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();

        //initialize modal
        initializeModal();
    }
}
