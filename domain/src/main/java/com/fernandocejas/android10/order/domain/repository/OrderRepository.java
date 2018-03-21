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
package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Order;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Order} related data.
 */
public interface OrderRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Order}.
     */
    Observable<List<Order>> orders(String token, String offset);

    /**
     * Get an {@link Observable} which will emit a {@link Order}.
     *
     * @param order_id The order id used to retrieve order data.
     */
    Observable<Order> order(String token, final String order_id);

    Observable<Order> create(String token, final Order order);

}
