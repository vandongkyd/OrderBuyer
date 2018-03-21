package com.fernandocejas.android10.restrofit.enity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jerry on Jan-18-18.
 */

public class GLocationEntity {

    @SerializedName("lat")
    private String lat;

    @SerializedName("lng")
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
