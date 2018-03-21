package com.fernandocejas.android10.restrofit.enity;

import com.google.gson.annotations.SerializedName;

public class MessageEntity {

    @SerializedName("datetime")
    private String datetime;

    @SerializedName("message")
    private String message;

    @SerializedName("order")
    private String order;

    @SerializedName("receiver")
    private String receiver;

    @SerializedName("sender")
    private String sender;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
