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
        this.imgName = avatar.getName();
        if (imgName.equals("B")) {
            this.name = imgName;
        } else {
            this.name = avatar.getName() + " Bee";
        }
    }

    public String getName() {
        return name;
    }

    public String getImgName() {
        return imgName;
    }


}
