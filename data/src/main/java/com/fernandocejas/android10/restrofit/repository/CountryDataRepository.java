/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.restrofit.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.order.domain.repository.CountryRepository;
import com.fernandocejas.android10.restrofit.enity.CountryEntity;
import com.fernandocejas.android10.restrofit.enity.mapper.AutoValueGsonFactory;
import com.fernandocejas.android10.restrofit.enity.mapper.CountryEntityDataMapper;
import com.fernandocejas.android10.sample.data.exception.NetworkConnectionException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class CountryDataRepository implements CountryRepository {


    private final CountryEntityDataMapper countryEntityDataMapper;

    private final Context context;

    @Inject
    CountryDataRepository(Context context,
                          CountryEntityDataMapper countryEntityDataMapper) {
        this.context = context;
        this.countryEntityDataMapper = countryEntityDataMapper;
    }

    @Override
    public Observable<List<Country>> countries() {

        return Observable.create(emitter -> {
            try {
                List<CountryEntity> responseUserEntities = getCountryEntitiesFromFile();
                if (responseUserEntities != null) {
                    emitter.onNext(countryEntityDataMapper.transform(
                            responseUserEntities));
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }
            } catch (Exception e) {
                emitter.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    private Gson createGSON() {
        return new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapterFactory(AutoValueGsonFactory.create())
                .create();
    }

    private List<CountryEntity> getCountryEntitiesFromFile() {
        String jsonString = loadJSONFromAsset();
        Gson gson = createGSON();
        List<CountryEntity> countryEntityList = gson
                .fromJson(jsonString, new TypeToken<List<CountryEntity>>() {
                }.getType());
        return countryEntityList;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.context.getAssets().open("countries.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
