package com.controllers.fxmlComponents;

import java.io.FileNotFoundException;

import com.components.AvatarItem;
import com.controllers.ApplicationController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * This class is the controller for the costume shop avatar.
 */
public class ShopAvatar extends ApplicationController {
    @FXML private ImageView avatar;
    @FXML private Label avatarName;

    private AvatarItem avatarItem;

    public void setData(AvatarItem avatar) {
        this.avatarItem = avatar;
        String name = avatarItem.getName();
        avatarName.setText(name);
        try {
            setImage(avatar.getImgName(), this.avatar);
        } catch (FileNotFoundException e) {
            System.err.println("File for avatar " + name + " not found.");
            e.printStackTrace();
        }
    }
}
