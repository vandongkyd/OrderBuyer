package com.fernandocejas.android10.restrofit.enity;

import com.google.gson.annotations.SerializedName;

/**
 *
 *
 */

public class PaymentEntity {

    @SerializedName("id")
    private int id;

    @SerializedName("userid")
    private String user_id;

    @SerializedName("cus_profile")
    private String cus_profile;

    @SerializedName("card_id")
    private String card_id;

    @SerializedName("brand")
    private String brand;

    @SerializedName("last4")
    private String last4;

    @SerializedName("country")
    private String country;

    @SerializedName("exp_month")
    private String exp_month;

    @SerializedName("exp_year")
    private String exp_year;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCus_profile() {
        return cus_profile;
    }

    public void setCus_profile(String cus_profile) {
        this.cus_profile = cus_profile;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExp_month() {
        return exp_month;
    }

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
