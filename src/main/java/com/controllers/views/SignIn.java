package com.controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.controllers.ApplicationController;
import com.enums.View;
import com.models.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the sigin view.
 */
public class SignIn extends ApplicationController implements Initializable {
    @FXML
    private ImageView backButton;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private ImageView signInButton;
    @FXML
    private ImageView createAccount;
    @FXML
    private ImageView incorrectMessage;

    private boolean signin() throws IOException{
        String username = usernameInput.getText();
        String pwd = passwordInput.getText();

        User user = new User();
        String res = user.login(username, pwd);

        if (res == null){
            //success
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();
        incorrectMessage.setVisible(false);

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MainApp.setRoot(View.MENU);
        });

        signInButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                boolean res = signin();

                if (res == true){
                    //signed in!!
                    incorrectMessage.setVisible(false);
                    MainApp.setRoot(View.PROFILE);
                } else {
                    //incorrect
                    incorrectMessage.setVisible(true);
                    
                }

            } catch (IOException exception){
                System.err.println("Unable to complete request");
            }
        });
    }

}
