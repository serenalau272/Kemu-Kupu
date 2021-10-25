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
    User currentUser;
    WheelTimer timer;

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
    Label nameLabel;
    @FXML
    Label starLabel;
    @FXML
    Label achievementsLabel;
    @FXML
    Label scoreLabel;
    @FXML
    TextField usernameInput;
    @FXML
    TextField nicknameInput;
    @FXML
    ImageView userAvatar;
    @FXML
    ImageView wheelButton;
    @FXML
    Label wheelLabel;

    private void configureStaticEntries() {
        starLabel.setText(Integer.toString(currentUser.getTotalStars()));
        achievementsLabel.setText(Integer.toString(currentUser.getNumAchievements()) + "/20");
        setAvatarImage(userAvatar);
    }

    private void configureDynamicEntries() {
        nameLabel.setText("Hello " + currentUser.getNickname() + "!");
        scoreLabel.setText(Integer.toString(currentUser.getHighScore()));
        nicknameInput.setText(currentUser.getNickname());
        usernameInput.setText(currentUser.getUsername());
        nicknameInput.setEditable(false);
        usernameInput.setEditable(false);
    }

    private void inputEditing(TextField input) {
        input.setEditable(true);
        input.setStyle("-fx-background-color: #7D6B50");
        input.requestFocus();
        input.positionCaret(input.getText().length());
    }

    private void addHandlers(ImageView editBtn, TextField input) {
        editBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            inputEditing(input);
        });

        input.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!usernameInput.getText().equals("")) {
                    try {
                        currentUser.setUsername(usernameInput.getText());
                    } catch (IOException e) {
                        Modal.showGeneralModal(ErrorModal.INTERNET);
                    }
                }
                if (!nicknameInput.getText().equals("") && !(nicknameInput.getText().length() > 10)) {
                    try {
                        currentUser.setNickname(nicknameInput.getText());
                    } catch (IOException e) {
                        Modal.showGeneralModal(ErrorModal.INTERNET);
                    }
                } else {
                    inputEditing(nicknameInput);
                    Modal.showGeneralModal(ErrorModal.NICKNAME);
                }
                usernameInput.setStyle("-fx-background-color: #DFC49B;");
                nicknameInput.setStyle("-fx-background-color: #DFC49B;");
                configureDynamicEntries();
            }
        });
    }

    @Override
    protected void start() {
        super.start();
        addHandlers(editNickname, nicknameInput);
        addHandlers(editUsername, usernameInput);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        currentUser = MainApp.getUser();

        nicknameInput.setFocusTraversable(false);
        usernameInput.setFocusTraversable(false);
        configureStaticEntries();
        configureDynamicEntries();

        timer = new WheelTimer(wheelLabel, null);
        timer.start();
        // Set event handlers

        this.signoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // TODO: yet to be fully linked
            Modal.showGeneralModal(ConfirmModal.SIGNOUT);
            // timer.stop();
        });
        this.resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            // TODO: endpoint to be created
            Modal.showGeneralModal(ConfirmModal.RESET);
            // timer.stop();
        });

        this.deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            Modal.showGeneralModal(ConfirmModal.DELETE);
            // timer.stop();
        });

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(Views.MENU);
            timer.stop();
        });
        this.shopButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(Views.SHOP);
            timer.stop();
        });
        this.achievementsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(Views.ACHIEVEMENT);
            timer.stop();
        });
        this.wheelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(Views.WHEEL);
            timer.stop();
        });
    }
}
