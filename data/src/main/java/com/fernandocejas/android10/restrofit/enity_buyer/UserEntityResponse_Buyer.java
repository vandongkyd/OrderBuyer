package com.fernandocejas.android10.restrofit.enity_buyer;

import android.support.annotation.Nullable;


import com.fernandocejas.android10.restrofit.enity.UserEntity;
import com.fernandocejas.android10.restrofit.enity.UserEntityResponse;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vandongluong on 3/12/18.
 */
@AutoValue
public abstract class UserEntityResponse_Buyer {

    @SerializedName("status")
    public abstract boolean status();

    @SerializedName("message")
    @Nullable
    public abstract String message();

    @SerializedName("data")
    @Nullable
    public abstract UserEntity_Buyer data();

    public static TypeAdapter<UserEntityResponse_Buyer> typeAdapter(Gson gson) {
        return new AutoValue_UserEntityResponse_Buyer.GsonTypeAdapter(gson);
    }
}
