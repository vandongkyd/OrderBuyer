/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.restrofit.enity;

import com.fernandocejas.android10.restrofit.enity_buyer.AddressEntity_Buyer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 *
 */

public class OrderEntity {

    @SerializedName("id")
    private String id;

    @SerializedName("userid")
    private String userid;

    @SerializedName("providerid")
    private String providerid;

    @SerializedName("amount")
    private String amount;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("weitght")
    private String weight;

    @SerializedName("tax")
    private String tax;

    @SerializedName("servicefee")
    private String servicefee;

    @SerializedName("deviverdate")
    private String deviverdate;

    @SerializedName("deliverto")
    private AddressEntity deliverto;

    @SerializedName("ios")
    private String ios;

    @SerializedName("country_name")
    private String country_name;

    @SerializedName("currency")
    private String currency;

    @SerializedName("status")
    private String status;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("products")
    private List<ProductEntity> products;

    @SerializedName("offers")
    private List<OfferEntity> offers;

    @SerializedName("country")
    private CountryEntity country;

    @SerializedName("description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProviderid() {
        return providerid;
    }

    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getServicefee() {
        return servicefee;
    }

    public void setServicefee(String servicefee) {
        this.servicefee = servicefee;
    }

    public String getDeviverdate() {
        return deviverdate;
    }

    public void setDeviverdate(String deviverdate) {
        this.deviverdate = deviverdate;
    }

    public AddressEntity getDeliverto() {
        return deliverto;
    }

    public void setDeliverto(AddressEntity deliverto) {
        this.deliverto = deliverto;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public List<OfferEntity> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferEntity> offers) {
        this.offers = offers;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
