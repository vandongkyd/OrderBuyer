package com.fernandocejas.android10.restrofit.enity_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Image_Buyer;
import com.fernandocejas.android10.restrofit.enity.CountryEntity;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

/**
 * Created by vandongluong on 3/14/18.
 */

public class ProductEntity_Buyer {
    @SerializedName("id")
    public String id ;
    @SerializedName("orderid")
    public String orderid ;
    @SerializedName("link")
    public String link ;
    @SerializedName("name")
    public String name ;
    @SerializedName("code")
    public String code ;
    @SerializedName("description")
    public String description ;
    @SerializedName("price")
    public String price ;
    @SerializedName("quantity")
    public String quantity ;
    @SerializedName("weight")
    public String weight ;
    @SerializedName("images")
    public List<ImageEntity_Buyer> images ;
    @SerializedName("country")
    private CountryEntity country;

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

    public List<ImageEntity_Buyer> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity_Buyer> images) {
        this.images = images;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }
}

