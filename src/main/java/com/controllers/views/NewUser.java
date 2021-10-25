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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the sigin view.
 */
public class NewUser extends ApplicationController implements Initializable {

    //// Properties ////
    @FXML
    private ImageView backButton;
    @FXML
    private TextField nicknameInput;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private ImageView createAccountButton;

    //// Private (helper) Methods ////

    /**
     * A handler for when an account is created.
     */
    private void __onCreateAccount() {
        User user;
        try {
            user = new User();
        } catch (IOException e1) {
            Modal.showGeneralModal(ErrorModal.INTERNET);
            return;
        }
        if (nicknameInput.getText().equals("") || (nicknameInput.getText().length() > 10)) {
            Modal.showGeneralModal(ErrorModal.NICKNAME);
            return;
        }
        try {
            String res = user.signup(usernameInput.getText(), passwordInput.getText(), nicknameInput.getText());
            if (res == null) {
                // success
                MainApp.setUser(user);
                MainApp.setRoot(Views.PROFILE);
            } else {
                // duplicate username
                Modal.showGeneralModal(ErrorModal.USERNAME);
                this.passwordInput.clear();
                this.usernameInput.clear();
                this.nicknameInput.clear();
                this.usernameInput.requestFocus();
            }
        } catch (IOException e) {
            Modal.showGeneralModal(ErrorModal.INTERNET);
            this.passwordInput.clear();
            this.usernameInput.clear();
            this.nicknameInput.clear();
            this.usernameInput.requestFocus();
        }
    }

    @Override
    protected void start() {
        nicknameInput.requestFocus();
    }

    //// Public Methods ////

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MainApp.setRoot(Views.SIGNIN);
        });

        this.createAccountButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            this.__onCreateAccount();
        });

        this.nicknameInput.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                usernameInput.requestFocus();
            }
        });

        this.usernameInput.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordInput.requestFocus();
            }
        });

        this.passwordInput.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.__onCreateAccount();
            }
        });

    }

}
