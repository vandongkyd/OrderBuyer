package com.fernandocejas.android10.restrofit.enity;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jerry on Feb-01-18.
 */

@AutoValue
public abstract class MetaDataEntityResponse {

    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract MetaDataEntity data();

    public static TypeAdapter<MetaDataEntityResponse> typeAdapter(Gson gson) {
        return new AutoValue_MetaDataEntityResponse.GsonTypeAdapter(gson);
    }
}
