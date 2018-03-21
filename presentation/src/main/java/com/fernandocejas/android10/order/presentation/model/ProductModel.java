package com.fernandocejas.android10.order.presentation.model;

import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.order.domain.Product;

import java.util.Collection;

/**
 * Class that represents a {@link Product} in the presentation layer.
 */

public class ProductModel {

    private String id;
    private String orderid;
    private String link;
    private String name;
    private String code;
    private String description;
    private String price;
    private String quantity;
    private String weight;
    private Collection<ImageModel> images;
    private String currency;
    private CountryModel country;
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

    public Collection<ImageModel> getImages() {
        return images;
    }

    public void setImages(Collection<ImageModel> imageModels) {
        this.images = imageModels;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public CountryModel getCountry() {
        return country;
    }

    public void setCountry(CountryModel country) {
        this.country = country;
    }

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }
}
