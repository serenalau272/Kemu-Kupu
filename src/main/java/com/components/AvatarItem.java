package com.components;

import com.enums.Avatar;

/**
 * AvatarItem is an object used to store all the information regarding one avatar costume type. This information is used 
 * to create the each costume component that is dynamically generated and added to the costume shop screen.
 */

public class AvatarItem {
    private Avatar avatar;
    private String name;
    private String imgName;

    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * Setter method that constructs the avatar's image file name and display name from the input enum
     * @param avatar
     */
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
        this.imgName = avatar.toString();
        if (imgName.equals("B")) {
            this.name = imgName;
        } else {
            this.name = avatar.toString() + " Bee";
        }
    }

    public String getName() {
        return name;
    }

    public String getImgName() {
        return imgName;
    }

}
