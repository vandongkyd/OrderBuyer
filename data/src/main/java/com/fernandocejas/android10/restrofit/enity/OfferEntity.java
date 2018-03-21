package com.fernandocejas.android10.restrofit.enity;

import com.fernandocejas.android10.order.domain.Address;
import com.google.gson.annotations.SerializedName;

/**
 * {@link com.fernandocejas.android10.order.domain.Offer} used in the data layer.
 */

public class OfferEntity {

    @SerializedName("providerid")
    private String providerid;

    @SerializedName("orderid")
    private String orderid;

    @SerializedName("description")
    private String description;

    @SerializedName("deviverdate")
    private String deviverdate;

    @SerializedName("providerfee")
    private String providerfee;

    @SerializedName("shipfee")
    private String shipfee;

    @SerializedName("surchargefee")
    private String surchargefee;

    @SerializedName("otherfee")
    private String otherfee;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logo;

    @SerializedName("website")
    private String website;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("rate")
    private RateEntity rate;

    @SerializedName("address")
    private AddressEntity address;

    public String getProviderid() {
        return providerid;
    }

    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviverdate() {
        return deviverdate;
    }

    public void setDeviverdate(String deviverdate) {
        this.deviverdate = deviverdate;
    }

    public String getProviderfee() {
        return providerfee;
    }

    public void setProviderfee(String providerfee) {
        this.providerfee = providerfee;
    }

    public String getShipfee() {
        return shipfee;
    }

    public void setShipfee(String shipfee) {
        this.shipfee = shipfee;
    }

    public String getSurchargefee() {
        return surchargefee;
    }

    public void setSurchargefee(String surchargefee) {
        this.surchargefee = surchargefee;
    }

    public String getOtherfee() {
        return otherfee;
    }

    public void setOtherfee(String otherfee) {
        this.otherfee = otherfee;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RateEntity getRate() {
        return rate;
    }

    public void setRate(RateEntity rate) {
        this.rate = rate;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
