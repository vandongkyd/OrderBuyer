package com.fernandocejas.android10.restrofit.enity;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;

/**
 * Created by jerry on Jan-18-18.
 */

public class GoogleGeoEntity {

    @SerializedName("address_components")
    private Collection<GAddressComponentsEntity> address_components;

    @SerializedName("formatted_address")
    private String formatted_address;

    @SerializedName("geometry")
    private GGeometryEntity geometry;

    public Collection<GAddressComponentsEntity> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(Collection<GAddressComponentsEntity> address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public GGeometryEntity getGeometry() {
        return geometry;
    }

    public void setGeometry(GGeometryEntity geometry) {
        this.geometry = geometry;
    }
}
