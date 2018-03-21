package com.fernandocejas.android10.restrofit.enity;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

/**
 * Created by jerry on Jan-18-18.
 */
@AutoValue
public abstract class GoogleGeoListEntityResponse {

    @SerializedName("results")
    @Nullable
    public abstract Collection<GoogleGeoEntity> results();

    public static TypeAdapter<GoogleGeoListEntityResponse> typeAdapter(Gson gson) {
        return new AutoValue_GoogleGeoListEntityResponse.GsonTypeAdapter(gson);
    }
}
