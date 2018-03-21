package com.fernandocejas.android10.order.domain.Model_buyer;

import com.fernandocejas.android10.order.domain.Country;

import java.util.Collection;

/**
 * Created by vandongluong on 3/14/18.
 */

public class Product_Buyer {
    public String id ;
    public String orderid ;
    public String link ;
    public String name ;
    public String code ;
    public String description ;
    public String price ;
    public String quantity ;
    public String weight ;
    public Collection<Image_Buyer> images ;
    public Country country;

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

    public Collection<Image_Buyer> getImages() {
        return images;
    }

    public void setImages(Collection<Image_Buyer> images) {
        this.images = images;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
