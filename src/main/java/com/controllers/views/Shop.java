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
import com.enums.Views;
import com.util.User;

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
    @FXML private ImageView backButton;
    @FXML private ImageView buyButton;
    @FXML private ImageView notEnough;
    @FXML private ImageView userAvatar;
    @FXML private ImageView avatarPrice;
    @FXML private Label avatarLabel;
    @FXML private Label starTotal;
    @FXML private ScrollPane scroll;
    @FXML private GridPane grid;

    private User currentUser;
    private Avatar chosenAvatar;

    private List<AvatarItem> avatars = new ArrayList<>();

    private List<AvatarItem> getData() {
        List<AvatarItem> avatars = new ArrayList<>();
        AvatarItem avatar;

        Avatar[] avatarTypes = {Avatar.DEFAULT, Avatar.WIZARD, Avatar.SAILOR, Avatar.CHEF, Avatar.MAGICIAN, 
            Avatar.QUEEN, Avatar.ALIEN, Avatar.FAIRY, Avatar.NINJA, Avatar.PROFESSOR};
        for (Avatar type : avatarTypes) {
            avatar = new AvatarItem();
            avatar.setAvatar(type);
            avatars.add(avatar);
        }
 
        return avatars;
    }

    private void setTextYellow(Node clickedAvatarName) {
        for (Node avatar : grid.getChildren()) {
            ((AnchorPane) avatar).getChildren().get(1).setStyle("-fx-text-fill: #FFFFFF");
        }
        clickedAvatarName.setStyle("-fx-text-fill: #FBB03B");
    }

    private void setChosenAvatar(Node clickedAvatarName) {
        setTextYellow(clickedAvatarName);
        String avatarName = ((Label) clickedAvatarName).getText().replace(" Bee", "");
        if (avatarName == "B") {
            setChosenAvatar(Avatar.fromString("default"));
        } else {
            setChosenAvatar(Avatar.fromString(avatarName));
        }
    }

    private void setChosenAvatar(Avatar avatar){
        chosenAvatar = avatar;

        if (currentUser.hasBeenPurchased(chosenAvatar)) {
            try {
                currentUser.setAvatar(avatar);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            setImage(chosenAvatar.toString(), userAvatar);
            if (currentUser.hasBeenPurchased(chosenAvatar)) {
                //@TODO
                buyButton.setVisible(false);
                notEnough.setVisible(false);
                avatarLabel.setVisible(true);
                avatarLabel.setText(chosenAvatar.getAvatarName());
                setImage("background", avatarPrice);
            } else {
                if (currentUser.canPurchase(chosenAvatar)){
                    buyButton.setVisible(true);
                    notEnough.setVisible(false);
                } else {
                    notEnough.setVisible(true);
                    buyButton.setVisible(false);
                }
                
                avatarLabel.setVisible(false);
                setImage(chosenAvatar.toString(), avatarPrice);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File for avatar " + chosenAvatar.toString() + " not found.");
            e.printStackTrace();
        }
    }

    private void setStars(){
        starTotal.setText(Integer.toString(currentUser.getTotalStars()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();
        currentUser = MainApp.getUser();
        notEnough.setVisible(false);

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(Views.PROFILE));  
        setStars();
        avatars.addAll(getData());

        buyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> {
            try {
                currentUser.unlockCostume(chosenAvatar);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            setStars();
            setChosenAvatar(chosenAvatar);
        });  

        int column = 0;
        int row = 1;
        try {
            for (int i=0; i<avatars.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainApp.class.getResource("/fxmlComponents/shopAvatar.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> setChosenAvatar(anchorPane.getChildren().get(1)));
                ShopAvatar shopAvatarController = fxmlLoader.getController();
                shopAvatarController.setData(avatars.get(i));

                if (column == 3) {
                    column = 0;
                    row ++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
