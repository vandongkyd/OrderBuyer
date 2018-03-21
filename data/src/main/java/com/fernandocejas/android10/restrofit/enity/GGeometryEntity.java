package com.fernandocejas.android10.restrofit.enity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jerry on Jan-18-18.
 */

public class GGeometryEntity {

    @SerializedName("location")
    private GLocationEntity location;

    public GLocationEntity getLocation() {
        return location;
    }

    public void setLocation(GLocationEntity location) {
        this.location = location;
    }
}
