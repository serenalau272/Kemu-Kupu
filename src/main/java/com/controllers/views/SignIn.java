package com.controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.controllers.ApplicationController;
import com.enums.ErrorModal;
import com.enums.Views;
import com.models.User;
import com.util.Modal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private boolean signin() throws IOException {
        String username = usernameInput.getText();
        String pwd = passwordInput.getText();

        User user = new User();
        String res = user.login(username, pwd);

        if (res == null) {
            // success
            MainApp.setUser(user);
            return true;
        } else {
            return false;
        }
    }

    public void onSignIn() {
        try {
            boolean res = signin();

            if (res == true) {
                // signed in!!
                incorrectMessage.setVisible(false);
                MainApp.setRoot(Views.PROFILE);
            } else {
                // incorrect
                incorrectMessage.setVisible(true);
                usernameInput.clear();
                passwordInput.clear();
                usernameInput.requestFocus();

            }

        } catch (IOException exception) {
            Modal.showGeneralModal(ErrorModal.INTERNET);
        }
    }

    @Override
    protected void start() {
        usernameInput.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();
        incorrectMessage.setVisible(false);
        usernameInput.clear();
        passwordInput.clear();

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MainApp.setRoot(Views.MENU);
        });

        usernameInput.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordInput.requestFocus();
            }
        });

        passwordInput.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onSignIn();
            }
        });

        signInButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            onSignIn();
        });

        createAccount.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MainApp.setRoot(Views.NEWUSER);
        });
    }

}
