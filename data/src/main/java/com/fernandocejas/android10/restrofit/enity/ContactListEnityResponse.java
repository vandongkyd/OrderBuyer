package com.fernandocejas.android10.restrofit.enity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by vandongluong on 3/9/18.
 */

@AutoValue
public abstract class ContactListEnityResponse {




    public static TypeAdapter<ContactListEnityResponse> typeAdapter(Gson gson) {
        return new AutoValue_ContactListEnityResponse.GsonTypeAdapter(gson);
    }
}
