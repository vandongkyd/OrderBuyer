package com.fernandocejas.android10.order.presentation.model;

import java.io.Serializable;

/**
 * Created by jerry on Feb-08-18.
 */

public class MessageModel implements Serializable {

    private String datetime;
    private String message;
    private long order;
    private long receiver;
    private long sender;

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

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }
}
