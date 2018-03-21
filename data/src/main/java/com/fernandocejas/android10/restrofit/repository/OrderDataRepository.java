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

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.repository.OrderRepository;
import com.fernandocejas.android10.restrofit.enity.OrderEntity;
import com.fernandocejas.android10.restrofit.enity.mapper.OrderEntityDataMapper;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link OrderRepository} for retrieving order data.
 */
@Singleton
public class OrderDataRepository implements OrderRepository {

    private final RetrofitHelper retrofitHelper;

    private final OrderEntityDataMapper orderEntityDataMapper;

    /**
     * Constructs a {@link OrderRepository}.
     *
     * @param retrofitHelper
     * @param orderEntityDataMapper
     */
    @Inject
    OrderDataRepository(
            RetrofitHelper retrofitHelper,
            OrderEntityDataMapper orderEntityDataMapper) {
        this.retrofitHelper = retrofitHelper;
        this.orderEntityDataMapper = orderEntityDataMapper;
    }

    @Override
    public Observable<List<Order>> orders(String token, String offset) {
        //we always get all orders from the cloud
        return retrofitHelper
                .getRestApiService()
                .orders("Bearer " + token, offset)
                .map(this.orderEntityDataMapper::transform);
    }

    @Override
    public Observable<Order> order(String token, String order_id) {
        return retrofitHelper
                .getRestApiService()
                .order_detail("Bearer " + token, order_id)
                .map(this.orderEntityDataMapper::transform);
    }

    @Override
    public Observable<Order> create(String token, Order order) {
        OrderEntity orderEntity =  orderEntityDataMapper.transform(order);
        return retrofitHelper
                .getRestApiService()
                .create_order("Bearer " + token, orderEntity)
                .map(this.orderEntityDataMapper::transform);
    }
}
