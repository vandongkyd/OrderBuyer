package com.fernandocejas.android10.order.domain;

import java.io.Serializable;

/**
 * Class that represents a {@link Image} in the domain layer.
 */

public class Image implements Serializable{

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
