package com.fernandocejas.android10.restrofit.enity_buyer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vandongluong on 3/14/18.
 */

public class ImageEntity_Buyer {
    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
