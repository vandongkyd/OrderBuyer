package com.fernandocejas.android10.restrofit.enity_buyer;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

/**
 * Created by vandongluong on 3/16/18.
 */
@AutoValue
public abstract class OfferListEntityResponse_Buyer {
    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract Collection<OfferEntity_Buyer> data();

    public static TypeAdapter<OfferListEntityResponse_Buyer> typeAdapter(Gson gson) {
        return new AutoValue_OfferListEntityResponse_Buyer.GsonTypeAdapter(gson);
    }
}
