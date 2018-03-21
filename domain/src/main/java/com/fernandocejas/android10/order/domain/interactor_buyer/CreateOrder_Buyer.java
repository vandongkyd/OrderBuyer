package com.fernandocejas.android10.order.domain.interactor_buyer;


import com.fernandocejas.android10.order.domain.Model_buyer.Order_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.OrderRepository_Buyer;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/14/18.
 */

public class CreateOrder_Buyer extends UseCase<Order_Buyer, CreateOrder_Buyer.Params> {

    private final OrderRepository_Buyer orderRepository_buyer;

    @Inject
    CreateOrder_Buyer(OrderRepository_Buyer orderRepository_buyer, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.orderRepository_buyer = orderRepository_buyer;
    }

    @Override
    protected Observable<Order_Buyer> buildUseCaseObservable(CreateOrder_Buyer.Params params) {
        Preconditions.checkNotNull(params);
        return this.orderRepository_buyer.create_buyer(params.token,
                params.order);
    }

    public static final class Params {

        final String token;
        final Order_Buyer order;

        private Params(String token,
                       Order_Buyer order) {
            this.token = token;
            this.order = order;
        }

        public static CreateOrder_Buyer.Params forCreateOrder(String token, Order_Buyer order) {
            return new CreateOrder_Buyer.Params(token, order);
        }
    }
}
