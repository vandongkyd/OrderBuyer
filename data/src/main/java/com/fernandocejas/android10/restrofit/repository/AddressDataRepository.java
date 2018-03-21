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

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.Event;
import com.fernandocejas.android10.order.domain.repository.AddressRepository;
import com.fernandocejas.android10.order.domain.repository.EventRepository;
import com.fernandocejas.android10.restrofit.enity.mapper.AddressEntityDataMapper;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AddressDataRepository implements AddressRepository {

    private final RetrofitHelper retrofitHelper;

    private final AddressEntityDataMapper addressEntityDataMapper;

    @Inject
    AddressDataRepository(
            RetrofitHelper retrofitHelper,
            AddressEntityDataMapper addressEntityDataMapper) {
        this.retrofitHelper = retrofitHelper;
        this.addressEntityDataMapper = addressEntityDataMapper;
    }

    @Override
    public Observable<List<Address>> addresses(String token) {
        return retrofitHelper
                .getRestApiService()
                .addresses("Bearer " + token)
                .map(this.addressEntityDataMapper::transform);
    }

    @Override
    public Observable<Address> addAddresses(String token, String address, String city, String postcode, String country, String country_code, String lat, String lng) {
        return retrofitHelper
                .getRestApiService()
                .new_address("Bearer " + token,
                        address,
                        city,
                        postcode,
                        country,
                        country_code,
                        lat,
                        lng)
                .map(this.addressEntityDataMapper::transform);
    }
}
