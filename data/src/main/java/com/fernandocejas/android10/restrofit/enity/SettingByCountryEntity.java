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
public abstract class SettingByCountryEntity {

    @SerializedName("id")
    @Nullable
    public abstract String id();

    @SerializedName("name")
    @Nullable
    public abstract String name();

    @SerializedName("code")
    @Nullable
    public abstract String code();

    @SerializedName("phonecode")
    @Nullable
    public abstract String phone_code();

    @SerializedName("currency")
    @Nullable
    public abstract String currency();

    @SerializedName("setting")
    @Nullable
    public abstract Collection<SettingEntity> setting();

    @SerializedName("addresses")
    @Nullable
    public abstract Collection<AddressEntity> addresses();

    public static TypeAdapter<SettingByCountryEntity> typeAdapter(Gson gson) {
        return new AutoValue_SettingByCountryEntity.GsonTypeAdapter(gson);
    }
}
