package com.fernandocejas.android10.order.presentation.model;

/**
 * Class that represents a {@link AddressModel} in the domain layer.
 */

public class AddressModel {

    private String id;
    private String address;
    private String city;
    private String state;
    private String postcode;
    private String code;
    private String country;
    private String lat;
    private String lng;
    private boolean is_header = false;

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

    public boolean isIs_header() {
        return is_header;
    }

    public void setIs_header(boolean is_header) {
        this.is_header = is_header;
    }
}
