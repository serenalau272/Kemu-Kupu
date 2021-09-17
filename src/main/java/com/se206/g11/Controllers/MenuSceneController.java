package com.se206.g11.Controllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MenuSceneController extends ApplicationController implements Initializable {
    
    @FXML
    private Label lblOut;
    
    @FXML
    private void btnClickAction(ActionEvent event) {
        lblOut.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
