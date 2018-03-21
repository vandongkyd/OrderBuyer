package com.fernandocejas.android10.restrofit.enity;

import com.google.gson.annotations.SerializedName;

/**
 *
 *
 */

public class ImageEntity {

    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
