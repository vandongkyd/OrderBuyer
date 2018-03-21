package com.fernandocejas.android10.restrofit.enity;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *
 *
 */

public class CountryEntity implements Serializable {

    @SerializedName("ID")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private String code;

    @SerializedName("dial_code")
    private String dial_code;

    @SerializedName("currency_name")
    private String currency_name;

    @SerializedName("currency_symbol")
    private String currency_symbol;

    @SerializedName("currency_code")
    private String currency_code;

    @SerializedName("ios")
    private String ios;

    @SerializedName("iso")
    private String iso;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(@Nullable String code) {
        this.code = code;
    }

    public String getDial_code() {
        return dial_code;
    }

    public void setDial_code(@Nullable String dial_code) {
        this.dial_code = dial_code;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(@Nullable String currency_name) {
        this.currency_name = currency_name;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(@Nullable String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(@Nullable String currency_code) {
        this.currency_code = currency_code;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(@Nullable String ios) {
        this.ios = ios;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(@Nullable String iso) {
        this.iso = iso;
    }
}
