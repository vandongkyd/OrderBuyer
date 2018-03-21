package com.fernandocejas.android10.restrofit.enity_buyer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vandongluong on 3/12/18.
 */

public class UserEntity_Buyer {

    @SerializedName("id")
    public String id ;
    @SerializedName("name")
    public String name ;
    @SerializedName("email")
    public String email ;
    @SerializedName("phone")
    public String phone ;
    @SerializedName("phonecode")
    public String phonecode ;
    @SerializedName("avatar")
    public String avatar ;
    @SerializedName("status")
    public String status ;
    @SerializedName("type")
    public String type ;
    @SerializedName("document")
    public String document ;
    @SerializedName("updated_at")
    public String updated_at ;
    @SerializedName("created_at")
    public String created_at ;
    @SerializedName("token")
    public String token ;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
