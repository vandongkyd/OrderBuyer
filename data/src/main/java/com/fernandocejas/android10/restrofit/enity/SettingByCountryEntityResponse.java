package com.fernandocejas.android10.restrofit.enity;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 *
 *
 */

@AutoValue
public abstract class SettingByCountryEntityResponse {

    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract SettingByCountryEntity data();

    public static TypeAdapter<SettingByCountryEntityResponse> typeAdapter(Gson gson) {
        return new AutoValue_SettingByCountryEntityResponse.GsonTypeAdapter(gson);
    }
}
