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

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Order}.
 */
public class GetOrderList extends UseCase<List<Order>, GetOrderList.Params> {

    private final OrderRepository orderRepository;

    @Inject
    GetOrderList(OrderRepository orderRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.orderRepository = orderRepository;
    }

    @Override
    protected Observable<List<Order>> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.orderRepository.orders(params.access_token, params.offset);
    }

    public static final class Params {

        private final String access_token;

        private final String offset;

        private Params(String access_token, String offset) {
            this.access_token = access_token;
            this.offset = offset;
        }

        public static GetOrderList.Params forOrder(String access_token, String offset) {
            return new GetOrderList.Params(access_token, offset);
        }
    }
}
