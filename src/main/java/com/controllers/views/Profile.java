package com.controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.animations.WheelTimer;
import com.controllers.ApplicationController;
import com.enums.ConfirmModal;
import com.enums.ErrorModal;
import com.enums.Views;
import com.models.User;
import com.util.Modal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * This class is the controller for the settings modal.
 */
public class Profile extends ApplicationController implements Initializable {
    //// Properties ////
    private User currentUser;
    private WheelTimer timer;

    @FXML
    ImageView backButton;
    @FXML
    ImageView editUsername;
    @FXML
    ImageView editNickname;
    @FXML
    ImageView deleteButton;
    @FXML
    ImageView resetButton;
    @FXML
    ImageView signoutButton;
    @FXML
    ImageView shopButton;
    @FXML
    ImageView achievementsButton;
    @FXML
    Label name;
    @FXML
    Label star;
    @FXML
    Label achievements;
    @FXML
    Label score;
    @FXML
    TextField usernameInput;
    @FXML
    TextField nicknameInput;
    @FXML
    ImageView userAvatar;
    @FXML
    ImageView wheelButton;
    @FXML
    Label wheel;

    //// Private (helper) Methods ////

    /**
     * Load data from the user into labels on the page
     */
    private void __configureStaticEntries() {
        this.star.setText(Integer.toString(this.currentUser.getTotalStars()));
        this.achievements.setText(Integer.toString(this.currentUser.getNumAchievements()) + "/20");
        setAvatarImage(userAvatar);
    }

    /**
     * Set all dynamic labels from the user, such as name, nickname, etc
     */
    private void __configureDynamicEntries() {
        this.name.setText("Hello " + this.currentUser.getNickname() + "!");
        this.score.setText(Integer.toString(this.currentUser.getHighScore()));
        this.nicknameInput.setText(this.currentUser.getNickname());
        this.usernameInput.setText(this.currentUser.getUsername());
        this.nicknameInput.setEditable(false);
        this.usernameInput.setEditable(false);
    }

    /**
     * Configure a provided textfield as editable
     * @param input the textfield to set as editable
     */
    private void __inputEditing(TextField input) {
        input.setEditable(true);
        input.setStyle("-fx-background-color: #7D6B50");
        input.requestFocus();
        input.positionCaret(input.getText().length());
    }

    /**
     * Initalise a button with handlers
     * @param editBtn the button to add handlers to
     * @param input the text field to link this button to
     */
    private void __addHandlers(ImageView editBtn, TextField input) {
        editBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            this.__inputEditing(input);
        });

        input.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!this.usernameInput.getText().equals("")) {
                    try {
                        this.currentUser.setUsername(usernameInput.getText());
                    } catch (IOException e) {
                        Modal.showGeneralModal(ErrorModal.INTERNET);
                    }
                }
                if (!this.nicknameInput.getText().equals("") && !(this.nicknameInput.getText().length() > 10)) {
                    try {
                        this.currentUser.setNickname(nicknameInput.getText());
                    } catch (IOException e) {
                        Modal.showGeneralModal(ErrorModal.INTERNET);
                    }
                } else {
                    this.__inputEditing(this.nicknameInput);
                    Modal.showGeneralModal(ErrorModal.NICKNAME);
                }
                usernameInput.setStyle("-fx-background-color: #DFC49B;");
                nicknameInput.setStyle("-fx-background-color: #DFC49B;");
                this.__configureDynamicEntries();
            }
        });
    }

    //// Public Methods ////

    @Override
    protected void start() {
        super.start();
        this.__addHandlers(editNickname, nicknameInput);
        this.__addHandlers(editUsername, usernameInput);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        this.currentUser = MainApp.getUser();

        this.nicknameInput.setFocusTraversable(false);
        this.usernameInput.setFocusTraversable(false);
        this.__configureStaticEntries();
        this.__configureDynamicEntries();

        this.timer = new WheelTimer(wheel, null);
        this.timer.start();
        // Set event handlers

        this.signoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Modal.showGeneralModal(ConfirmModal.SIGNOUT);
        });
        this.resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Modal.showGeneralModal(ConfirmModal.RESET);
        });

        this.deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Modal.showGeneralModal(ConfirmModal.DELETE);
        });

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(Views.MENU);
            this.timer.stop();
        });

        this.shopButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(Views.SHOP);
            this.timer.stop();
        });

        this.achievementsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(Views.ACHIEVEMENT);
            this.timer.stop();
        });

        this.wheelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(Views.WHEEL);
            this.timer.stop();
        });
    }
}
