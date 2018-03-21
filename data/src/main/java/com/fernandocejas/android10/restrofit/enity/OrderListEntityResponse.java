package com.fernandocejas.android10.restrofit.enity;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

/**
 *
 *
 */

@AutoValue
public abstract class OrderListEntityResponse {

    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract Collection<OrderEntity> data();

    public static TypeAdapter<OrderListEntityResponse> typeAdapter(Gson gson) {
        return new AutoValue_OrderListEntityResponse.GsonTypeAdapter(gson);
    }
}
