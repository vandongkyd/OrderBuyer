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

import com.fernandocejas.android10.order.domain.Event;
import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.repository.EventRepository;
import com.fernandocejas.android10.order.domain.repository.OrderRepository;
import com.fernandocejas.android10.restrofit.enity.mapper.EventEntityDataMapper;
import com.fernandocejas.android10.restrofit.enity.mapper.OrderEntityDataMapper;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class EventDataRepository implements EventRepository {

    private final RetrofitHelper retrofitHelper;

    private final EventEntityDataMapper eventEntityDataMapper;

    @Inject
    EventDataRepository(
            RetrofitHelper retrofitHelper,
            EventEntityDataMapper eventEntityDataMapper) {
        this.retrofitHelper = retrofitHelper;
        this.eventEntityDataMapper = eventEntityDataMapper;
    }

    @Override
    public Observable<List<Event>> events(String token) {
        return retrofitHelper
                .getRestApiService()
                .events("Bearer " + token)
                .map(this.eventEntityDataMapper::transform);
    }
}
