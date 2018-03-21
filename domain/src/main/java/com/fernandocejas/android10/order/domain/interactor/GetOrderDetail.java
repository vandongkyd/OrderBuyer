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

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a detail information of {@link Order}.
 */
public class GetOrderDetail extends UseCase<Order, GetOrderDetail.Params> {

    private final OrderRepository orderRepository;

    @Inject
    GetOrderDetail(OrderRepository orderRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.orderRepository = orderRepository;
    }

    @Override
    protected Observable<Order> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.orderRepository.order(params.access_token, params.order_id);
    }

    public static final class Params {

        private final String access_token;

        private final String order_id;

        private Params(String access_token, String order_id) {
            this.access_token = access_token;
            this.order_id = order_id;
        }

        public static GetOrderDetail.Params forOrderDetail(String access_token, String order_id) {
            return new GetOrderDetail.Params(access_token, order_id);
        }
    }
}
