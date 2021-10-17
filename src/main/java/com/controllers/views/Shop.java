package com.controllers.views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.AvatarItem;
import com.controllers.ModalController;
import com.controllers.fxmlComponents.ShopAvatar;
import com.enums.Avatar;
import com.enums.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class Shop extends ModalController {
    @FXML private ImageView backButton;
    @FXML private ImageView buyButton;
    @FXML private ImageView userAvatar;
    @FXML private ImageView avatarPrice;
    @FXML private Label avatarLabel;
    @FXML private ScrollPane scroll;
    @FXML private GridPane grid;

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

        try {
            setImage(avatarName, userAvatar);
            if (avatarName.equals("B")) {
                //@TODO
                buyButton.setVisible(false);
                avatarLabel.setVisible(true);
                avatarLabel.setText(avatarName);
                setImage("background", avatarPrice);
            } else {
                buyButton.setVisible(true);
                avatarLabel.setVisible(false);
                setImage(avatarName, avatarPrice);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File for avatar " + avatarName + " not found.");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.PROFILE));  
      
        avatars.addAll(getData());

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
