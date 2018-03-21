package com.fernandocejas.android10.restrofit.enity_buyer;

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.order.domain.Model_buyer.Address_Buyer;
import com.fernandocejas.android10.order.domain.Model_buyer.Offer_Buyer;
import com.fernandocejas.android10.order.domain.Model_buyer.Product_Buyer;
import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.restrofit.enity.CountryEntity;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

/**
 * Created by vandongluong on 3/14/18.
 */

public class OrderEnity_Buyer {
    @SerializedName("id")
    private String id ;
    @SerializedName("userid")
    private String userid ;
    @SerializedName("providerid")
    private String providerid ;
    @SerializedName("amount")
    private String amount ;
    @SerializedName("quantity")
    private String quantity ;
    @SerializedName("weitght")
    private String weitght ;
    @SerializedName("tax_percent")
    private String tax_percent ;
    @SerializedName("tax_amount")
    private String tax_amount ;
    @SerializedName("deviverdate")
    private String deviverdate ;
    @SerializedName("deliverto")
    private AddressEntity_Buyer deliverto ;
    @SerializedName("ios")
    private String ios ;
    @SerializedName("country_name")
    private String country_name ;
    @SerializedName("currency")
    private String currency ;
    @SerializedName("description")
    private String description ;
    @SerializedName("status")
    private String status ;
    @SerializedName("created_at")
    private String created_at ;
    @SerializedName("ship_from_country")
    private String ship_from_country ;
    @SerializedName("ship_to_country")
    private String ship_to_country ;
    @SerializedName("products")
    private Collection<ProductEntity_Buyer> products ;
    @SerializedName("offers")
    private Collection<OfferEntity_Buyer> offers ;
    @SerializedName("country")
    private CountryEntity country ;

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

    public String getWeitght() {
        return weitght;
    }

    public void setWeitght(String weitght) {
        this.weitght = weitght;
    }

    public String getTax_percent() {
        return tax_percent;
    }

    public void setTax_percent(String tax_percent) {
        this.tax_percent = tax_percent;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }

    public String getDeviverdate() {
        return deviverdate;
    }

    public void setDeviverdate(String deviverdate) {
        this.deviverdate = deviverdate;
    }

    public AddressEntity_Buyer getDeliverto() {
        return deliverto;
    }

    public void setDeliverto(AddressEntity_Buyer address_buyer) {
        this.deliverto = address_buyer;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getShip_from_country() {
        return ship_from_country;
    }

    public void setShip_from_country(String ship_from_country) {
        this.ship_from_country = ship_from_country;
    }

    public String getShip_to_country() {
        return ship_to_country;
    }

    public void setShip_to_country(String ship_to_country) {
        this.ship_to_country = ship_to_country;
    }

    public Collection<ProductEntity_Buyer> getProducts() {
        return products;
    }

    public void setProducts(Collection<ProductEntity_Buyer> products) {
        this.products = products;
    }

    public Collection<OfferEntity_Buyer> getOffers() {
        return offers;
    }

    public void setOffers(Collection<OfferEntity_Buyer> offers) {
        this.offers = offers;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

}


