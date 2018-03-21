package com.fernandocejas.android10.restrofit.enity_buyer;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vandongluong on 3/14/18.
 */
@AutoValue
public abstract class AddressEntityResponse_Buyer {
    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract AddressEntity_Buyer data();

    public static TypeAdapter<AddressEntityResponse_Buyer> typeAdapter(Gson gson) {
        return new AutoValue_AddressEntityResponse_Buyer.GsonTypeAdapter(gson);
    }
}
