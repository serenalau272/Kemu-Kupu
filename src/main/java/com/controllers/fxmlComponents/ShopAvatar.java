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
    @FXML
    private ImageView avatar;
    @FXML
    private Label avatarName;

    /**
     * Retrieve information from the input AvatarItem object and modify this ShopAvatar component
     * accordingly. This component will then be dynamically added to the Costume shop screen.
     * @param avatar
     */
    public void setData(AvatarItem avatar) {
        // Capitalise first letter of name to avoid inconsistency errors 
        String name = avatar.getName().substring(0, 1).toUpperCase() + avatar.getName().substring(1);

        // This costume is the only one with a different name to its display name, so processed differently
        if (name.equals("Default Bee")) {
            name = "B";
        }
        
        // set avatar label to be the retrieved name
        avatarName.setText(name);
        try {
            // set avatar image using the retrieved file image name
            setImage(avatar.getImgName(), this.avatar);
        } catch (FileNotFoundException e) {
            System.err.println("File for avatar " + name + " not found.");
            e.printStackTrace();
        }
    }
}
