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
package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.repository.OrderRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CreateOrder extends UseCase<Order, CreateOrder.Params> {

    private final OrderRepository orderRepository;

    @Inject
    CreateOrder(OrderRepository orderRepository, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.orderRepository = orderRepository;
    }

    @Override
    protected Observable<Order> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.orderRepository.create(params.token,
                params.order);
    }

    public static final class Params {

        final String token;
        final Order order;

        private Params(String token,
                       Order order) {
            this.token = token;
            this.order = order;
        }

        public static CreateOrder.Params forCreateOrder(String token, Order order) {
            return new CreateOrder.Params(token, order);
        }
    }
}
