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

import com.fernandocejas.android10.order.domain.GoogleGeo;
import com.fernandocejas.android10.order.domain.repository.GoogleGeoRepository;
import com.fernandocejas.android10.restrofit.enity.mapper.GoogleGeoEntityDataMapper;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper;
import com.fernandocejas.android10.sample.data.BuildConfig;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class GoogleGeoDataRepository implements GoogleGeoRepository {

    private final RetrofitHelper retrofitHelper;

    private final GoogleGeoEntityDataMapper googleGeoEntityDataMapper;

    @Inject
    GoogleGeoDataRepository(
            RetrofitHelper retrofitHelper,
            GoogleGeoEntityDataMapper googleGeoEntityDataMapper) {
        this.retrofitHelper = retrofitHelper;
        this.googleGeoEntityDataMapper = googleGeoEntityDataMapper;
    }


    @Override
    public Observable<List<GoogleGeo>> geocode(String lat, String lng) {
        return retrofitHelper
                .getRestApiService()
                .geocode(lat + "," + lng, BuildConfig.GOOGLE_API_KEY)
                .map(this.googleGeoEntityDataMapper::transform);
    }
}
