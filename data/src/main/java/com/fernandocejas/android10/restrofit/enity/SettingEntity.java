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
public abstract class SettingEntity {

    @SerializedName("id")
    @Nullable
    public abstract String id();

    @SerializedName("code")
    @Nullable
    public abstract String code();

    @SerializedName("description")
    @Nullable
    public abstract String description();

    @SerializedName("values")
    @Nullable
    public abstract String values();

    public static TypeAdapter<SettingEntity> typeAdapter(Gson gson) {
        return new AutoValue_SettingEntity.GsonTypeAdapter(gson);
    }
}
