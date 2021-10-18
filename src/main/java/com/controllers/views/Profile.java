package com.controllers.views;
import java.net.URL;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.animations.WheelTimer;
import com.controllers.ApplicationController;
import com.enums.ConfirmModal;
import com.enums.View;
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

    @FXML ImageView backButton;
    @FXML ImageView editUsername;
    @FXML ImageView editNickname;
    @FXML ImageView passwordButton;
    @FXML ImageView resetButton;
    @FXML ImageView signoutButton;
    @FXML ImageView shopButton;
    @FXML ImageView achievementsButton;
    @FXML Label nameLabel;
    @FXML Label starLabel;
    @FXML Label achievementsLabel;
    @FXML Label scoreLabel;
    @FXML TextField usernameInput;
    @FXML TextField nicknameInput;
    @FXML ImageView userAvatar;
    @FXML ImageView wheelButton;
    @FXML Label wheelLabel;

    

    private void configureStaticEntries(){
        nameLabel.setText("Hello " + currentUser.getNickname()+"!");
        starLabel.setText(Integer.toString(currentUser.getTotalStars()));
        achievementsLabel.setText(Integer.toString(currentUser.getNumAchievements()) + "/18");
        setAvatarImage(userAvatar);
    }

    private void configureDynamicEntries(){
        scoreLabel.setText(Integer.toString(currentUser.getHighScore()));
        nicknameInput.setText(currentUser.getNickname());
        usernameInput.setText(currentUser.getUsername());
        nicknameInput.setEditable(false);
        usernameInput.setEditable(false);
    }

    private void addHandlers(ImageView editBtn, TextField input){
        editBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            input.setEditable(true);
            input.setStyle("-fx-background-color: orange;");
            input.requestFocus();
            input.positionCaret(input.getText().length());
        });

        input.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER){
                if (!usernameInput.getText().equals("")){
                    currentUser.setUsername(usernameInput.getText());
                }
                if (!nicknameInput.getText().equals("")){
                    currentUser.setNickname(nicknameInput.getText());
                }
                usernameInput.setStyle("-fx-background-color: blue;");
                nicknameInput.setStyle("-fx-background-color: blue;");
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

        System.out.println(currentUser.getUsername());
        
        nicknameInput.setFocusTraversable(false);
        usernameInput.setFocusTraversable(false);
        configureStaticEntries();
        configureDynamicEntries();

        timer = new WheelTimer(wheelLabel);
        timer.start();
        //Set event handlers

        this.signoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO: yet to be fully linked
            Modal.showConfirmationModal(ConfirmModal.SIGNOUT);
            // timer.stop();
        });
        this.resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            //TODO: are we still using hidden for high score??
            // currentUser.setHighScore(0);
            configureDynamicEntries();
            Modal.showConfirmationModal(ConfirmModal.RESET);
            // timer.stop();
        });

        this.passwordButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            //TODO: IDEK HOW THIS GONNA WORK
            timer.stop();
        });

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event ->{
            MainApp.setRoot(View.MENU);
            timer.stop();
        });
        this.shopButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(View.SHOP);
            timer.stop();
        });
        this.achievementsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(View.ACHIEVEMENT);
            timer.stop();
        });
        this.wheelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            MainApp.setRoot(View.WHEEL);
            timer.stop();
        });
    }
}
