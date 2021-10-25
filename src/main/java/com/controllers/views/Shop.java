package com.controllers.views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.AvatarItem;
import com.controllers.ApplicationController;
import com.controllers.fxmlComponents.ShopAvatar;
import com.enums.Avatar;
import com.enums.ErrorModal;
import com.enums.Views;
import com.models.User;
import com.util.Modal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * This class is the controller for the costume shop.
 */
public class Shop extends ApplicationController implements Initializable {
    //// Properties ////
    @FXML
    private ImageView backButton;
    @FXML
    private ImageView buyButton;
    @FXML
    private ImageView notEnough;
    @FXML
    private ImageView userAvatar;
    @FXML
    private ImageView avatarPrice;
    @FXML
    private Label avatarMessage;
    @FXML
    private Label starTotal;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;

    private User currentUser;
    private Avatar chosenAvatar;

    private List<AvatarItem> avatars = new ArrayList<>();

    //// Private Methods ////

    /**
     * Retrieve information from the data stored for the current user and dynamically generate the costume types
     * accordingly.
     * @return
     */
    private List<AvatarItem> __getData() {
        List<AvatarItem> newAvatars = new ArrayList<>();
        AvatarItem avatar;
        // Loop through all the different costume types to create an AvatarItem object to store information from the enum 
        // and add object to the avatars list
        for (Avatar type : Avatar.values()) {
            avatar = new AvatarItem();
            avatar.setAvatar(type);
            newAvatars.add(avatar);
        }

        return newAvatars;
    }
    
    /**
     * Method to set the avatar label text to yellow (using CSS) when it has been clicked on
     * @param clickedAvatarName
     */
    private void __setTextYellow(Node clickedAvatarName) {
        // Loop through all shop avatars on the screen and set display label to be white
        for (Node avatar : this.grid.getChildren()) {
            Label label = ((Label) ((AnchorPane) avatar).getChildren().get(1));
            label.getStyleClass().remove("yellow-text");
        }
        // Set the selected shop avatar's display label to be yellow
        clickedAvatarName.getStyleClass().add("yellow-text");
    }

    /**
     * Method to filter the name of the selected avatar for placing the selected shop avatar on the stage area (when clicked on)
     * @param clickedAvatarName
     */
    private void __setChosenAvatar(Node clickedAvatarName) {
        this.__setTextYellow(clickedAvatarName);
        String avatarName = ((Label) clickedAvatarName).getText().replace(" Bee", "");
        if (avatarName == "B") {
            this.__setChosenAvatar(Avatar.fromString("default"));
        } else {
            this.__setChosenAvatar(Avatar.fromString(avatarName));
        }
    }

    /**
     * Method to place the selected shop avatar on the stage area (when clicked on)
     * @param avatar
     */
    private void __setChosenAvatar(Avatar avatar) {
        this.chosenAvatar = avatar;
        // If the user owns the avatar, then update the current user's avatar to be the one selected
        if (this.currentUser.hasBeenPurchased(this.chosenAvatar)) {
            try {
                this.currentUser.setAvatar(avatar);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
        }

        try {
            this.setImage(this.chosenAvatar.toString(), this.userAvatar);
            // If the user owns the avatar, set the 'stage' label to display only the costume name
            if (this.currentUser.hasBeenPurchased(this.chosenAvatar)) {
                this.buyButton.setVisible(false);
                this.notEnough.setVisible(false);
                this.avatarMessage.setVisible(true);
                this.avatarMessage.setText(this.chosenAvatar.getAvatarName());
                this.setImage("background", this.avatarPrice);
            } else {
                // If the user does not own the avatar, set the 'stage' label to display the price and 
                // whether or not the user has enough stars to purchase it.
                if (this.currentUser.canPurchase(this.chosenAvatar)) {
                    this.buyButton.setVisible(true);
                    this.notEnough.setVisible(false);
                } else {
                    this.notEnough.setVisible(true);
                    this.buyButton.setVisible(false);
                }

                this.avatarMessage.setVisible(false);
                setImage(this.chosenAvatar.toString(), this.avatarPrice);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File for avatar " + this.chosenAvatar.toString() + " not found.");
            e.printStackTrace();
        }
    }

    // Update the user's total star count
    private void __setStars() {
        this.starTotal.setText(Integer.toString(this.currentUser.getTotalStars()));
    }

    //// Public Methods ////

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();
        this.currentUser = MainApp.getUser();
        this.notEnough.setVisible(false);

        // Set the stage avatar to be the user's currently set avatar
        try {
            setImage(this.currentUser.getSelectedAvatar().toString(), this.userAvatar);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to set avatar on load");
        }
        // Configure screen information display and components
        this.avatarMessage.setText(currentUser.getSelectedAvatar().getAvatarName());

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(Views.PROFILE));
        this.__setStars();
        this.avatars.addAll(this.__getData());

        // Configure eventHandler for the buy button; allow user to unlock a costume when clicked
        this.buyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            try {
                this.currentUser.unlockCostume(this.chosenAvatar);
            } catch (IOException e) {
                Modal.showGeneralModal(ErrorModal.INTERNET);
            }
            this.__setStars();
            this.__setChosenAvatar(chosenAvatar);
        });

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < avatars.size(); i++) {
                // Load the ShopAvatar AnchorPane to retrieve its controller, modify its information and dynamically add to
                // the Shop screen
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainApp.class.getResource("/fxmlComponents/shopAvatar.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        _event -> this.__setChosenAvatar(anchorPane.getChildren().get(1)));
                ShopAvatar shopAvatarController = fxmlLoader.getController();

                // use the ShopAvatar controller to generate an avatar costume type component using the retrieved information
                // and add to the Shop screen GridPane
                shopAvatarController.setData(avatars.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                // Set grid size and alignment
                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


/**
 * Attributions:
 * Dynamic GridPane design inspired by Mahmoud Hamwi's implementation
 * GitHub repo: https://github.com/mahmoudhamwi/Fruits-Market
 */