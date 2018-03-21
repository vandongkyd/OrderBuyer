package com.fernandocejas.android10.restrofit.enity;

import android.support.annotation.Nullable;

import com.fernandocejas.android10.order.domain.Address;
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
public abstract class AddressListEntityResponse {

    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract Collection<AddressEntity> data();

    public static TypeAdapter<AddressListEntityResponse> typeAdapter(Gson gson) {
        return new AutoValue_AddressListEntityResponse.GsonTypeAdapter(gson);
    }
}
