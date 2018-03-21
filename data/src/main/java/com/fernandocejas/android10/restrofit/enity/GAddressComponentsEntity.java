package com.fernandocejas.android10.restrofit.enity;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;

/**
 * Created by jerry on Jan-18-18.
 */

public class GAddressComponentsEntity {

    @SerializedName("long_name")
    private String long_name;

    @SerializedName("short_name")
    private String short_name;

    @SerializedName("types")
    private Collection<String> types;

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public Collection<String> getTypes() {
        return types;
    }

    public void setTypes(Collection<String> types) {
        this.types = types;
    }
}
