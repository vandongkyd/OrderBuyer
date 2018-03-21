package com.fernandocejas.android10.order.domain;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 *
 */

public class SettingByCountry implements Serializable{

    private String id;
    private String name;
    private String code;
    private String phone_code;
    private String currency;
    private Collection<Setting> setting;
    private Collection<Address> addresses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Collection<Setting> getSetting() {
        return setting;
    }

    public void setSetting(Collection<Setting> setting) {
        this.setting = setting;
    }

    public Collection<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<Address> addresses) {
        this.addresses = addresses;
    }
}
