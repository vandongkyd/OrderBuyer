package com.fernandocejas.android10.restrofit.enity_buyer;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vandongluong on 3/14/18.
 */
@AutoValue
public abstract class ProductEntityResponse_Buyer implements Serializable {

    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract ProductEntity_Buyer data();

    public static TypeAdapter<ProductEntityResponse_Buyer> typeAdapter(Gson gson) {
        return new AutoValue_ProductEntityResponse_Buyer.GsonTypeAdapter(gson);
    }
}

