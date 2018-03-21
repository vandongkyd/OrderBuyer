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
public abstract class EventEntity implements Serializable {

    @SerializedName("id")
    @Nullable
    public abstract String id();

    @SerializedName("image")
    @Nullable
    public abstract String image();

    @SerializedName("link")
    @Nullable
    public abstract String link();

    @SerializedName("description")
    @Nullable
    public abstract String description();

    @SerializedName("active")
    @Nullable
    public abstract String active();

    @SerializedName("created_at")
    @Nullable
    public abstract String created_at();

    @SerializedName("updated_at")
    @Nullable
    public abstract String updated_at();

    public static TypeAdapter<EventEntity> typeAdapter(Gson gson) {
        return new AutoValue_EventEntity.GsonTypeAdapter(gson);
    }
}
