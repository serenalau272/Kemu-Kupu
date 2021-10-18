package com.components;

import com.enums.Avatar;

public class AvatarItem {
    private Avatar avatar;
    private String name;
    private String imgName;

    public Avatar getAvatar() {
        return avatar;
    }

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
