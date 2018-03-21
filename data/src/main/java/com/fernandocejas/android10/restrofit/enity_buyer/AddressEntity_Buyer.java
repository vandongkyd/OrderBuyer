package com.fernandocejas.android10.restrofit.enity_buyer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vandongluong on 3/14/18.
 */

public class AddressEntity_Buyer {
    @SerializedName("id")
    public String id ;
    @SerializedName("address")
    public String address ;
    @SerializedName("city")
    public String city ;
    @SerializedName("state")
    public String state ;
    @SerializedName("postcode")
    public String postcode ;
    @SerializedName("code")
    public String code ;
    @SerializedName("country")
    public String country ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
