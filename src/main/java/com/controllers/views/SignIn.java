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
    //// Properties ////
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

    //// Private Methods ////

    /**
     * Attempts to sign in a user with the provided values from the next fields
     * @return a boolean indicating which is true if the user was signed in, false otherwise
     * @throws IOException if unable to contact the API
     */
    private boolean __signin() throws IOException {
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

    //// Public Methods ////

    /**
     * Button handler for when the sign in button is clicked.
     */
    public void onSignIn() {
        try {
            boolean res = this.__signin();

            if (res == true) {
                // signed in!!
                this.incorrectMessage.setVisible(false);
                MainApp.setRoot(Views.PROFILE);
            } else {
                // incorrect
                this.incorrectMessage.setVisible(true);
                this.usernameInput.clear();
                this.passwordInput.clear();
                this.usernameInput.requestFocus();
            }

        } catch (IOException exception) {
            Modal.showGeneralModal(ErrorModal.INTERNET);
            this.usernameInput.clear();
            this.passwordInput.clear();
            this.usernameInput.requestFocus();
        }
    }

    @Override
    protected void start() {
        this.usernameInput.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();
        this.incorrectMessage.setVisible(false);
        this.usernameInput.clear();
        this.passwordInput.clear();

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MainApp.setRoot(Views.MENU);
        });

        this.usernameInput.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.passwordInput.requestFocus();
            }
        });

        this.passwordInput.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.onSignIn();
            }
        });

        this.signInButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            this.onSignIn();
        });

        this.createAccount.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MainApp.setRoot(Views.NEWUSER);
        });
    }

}
