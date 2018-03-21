package com.fernandocejas.android10.restrofit.enity;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vandongluong on 3/9/18.
 */
@AutoValue
public abstract class ContactEnityResponse {
    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract ContactEnity data();

    public static TypeAdapter<ContactEnityResponse> typeAdapter(Gson gson) {
        return new AutoValue_ContactEnityResponse.GsonTypeAdapter(gson);
    }
}
