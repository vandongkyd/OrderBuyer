package com.fernandocejas.android10.order.domain;

import java.io.Serializable;

/**
 *
 *
 */

public class Setting implements Serializable{

    private String id;
    private String code;
    private String description;
    private String values;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
