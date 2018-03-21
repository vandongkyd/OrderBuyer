package com.fernandocejas.android10.order.presentation.utils;

import java.io.Serializable;
import java.util.List;

public class Wrapper implements Serializable {

    private List<?> parliaments;

    public Wrapper(List<?> data) {
        this.parliaments = data;
    }

    public List<?> getParliaments() {
        return this.parliaments;
    }

}
