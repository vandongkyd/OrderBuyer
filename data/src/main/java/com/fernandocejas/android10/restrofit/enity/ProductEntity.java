package com.fernandocejas.android10.restrofit.enity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 *
 */

public class ProductEntity {

    @SerializedName("id")
    private String id;

    @SerializedName("orderid")
    private String orderid;

    @SerializedName("link")
    private String link;

    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private String code;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private String price;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("weight")
    private String weight;

    @SerializedName("images")
    private List<ImageEntity> images;

    @SerializedName("currency")
    private String currency;

    @SerializedName("country")
    private CountryEntity country;

    @SerializedName("weight_unit")
    private String weight_unit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }
}
