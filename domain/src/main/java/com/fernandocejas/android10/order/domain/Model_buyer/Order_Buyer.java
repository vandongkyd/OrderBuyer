package com.fernandocejas.android10.order.domain.Model_buyer;

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.order.domain.Product;

import java.util.Collection;

/**
 * Created by vandongluong on 3/14/18.
 */

public class Order_Buyer {
    private String id ;
    private String userid ;
    private String providerid ;
    private String amount ;
    private String quantity ;
    private String weitght ;
    private String tax_percent ;
    private String tax_amount ;
    private String deviverdate ;
    private Address_Buyer address_buyer ;
    private String ios ;
    private String country_name ;
    private String currency ;
    private String description ;
    private String status ;
    private String created_at ;
    private String ship_from_country ;
    private String ship_to_country ;
    private Collection<Product_Buyer> products ;
    private Collection<Offer_Buyer> offers ;
    private Country country;

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

    public Address_Buyer getAddress_buyer() {
        return address_buyer;
    }

    public void setAddress_buyer(Address_Buyer address_buyer) {
        this.address_buyer = address_buyer;
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

    public Collection<Product_Buyer> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product_Buyer> products) {
        this.products = products;
    }

    public Collection<Offer_Buyer> getOffers() {
        return offers;
    }

    public void setOffers(Collection<Offer_Buyer> offers) {
        this.offers = offers;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}