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

    public void setData(AvatarItem avatar) {
        String name = avatar.getName().substring(0, 1).toUpperCase() + avatar.getName().substring(1);
        avatarName.setText(name);
        try {
            setImage(avatar.getImgName(), this.avatar);
        } catch (FileNotFoundException e) {
            System.err.println("File for avatar " + name + " not found.");
            e.printStackTrace();
        }
    }
}
