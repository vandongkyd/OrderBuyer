package com.fernandocejas.android10.restrofit.enity;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *
 *
 */

@AutoValue
public abstract class ProductEntityParseResponse implements Serializable {

    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract ProductEntity data();

    public static TypeAdapter<ProductEntityParseResponse> typeAdapter(Gson gson) {
        return new AutoValue_ProductEntityParseResponse.GsonTypeAdapter(gson);
    }
}
