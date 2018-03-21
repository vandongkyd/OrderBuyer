package com.fernandocejas.android10.order.domain;

import java.io.Serializable;

/**
 *
 *
 */

public class Rate implements Serializable{
    private String start;

    private String count;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
