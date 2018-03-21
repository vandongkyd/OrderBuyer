package com.fernandocejas.android10.order.domain.interactor_buyer;


import com.fernandocejas.android10.order.domain.Model_buyer.Order_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.OrderRepository_Buyer;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/14/18.
 */

public class GetOrderList_Buyer extends UseCase<List<Order_Buyer>, GetOrderList_Buyer.Params> {

    private final OrderRepository_Buyer orderRepository_buyer;

    @Inject
    GetOrderList_Buyer(OrderRepository_Buyer orderRepository_buyer, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.orderRepository_buyer = orderRepository_buyer;
    }

    @Override
    protected Observable<List<Order_Buyer>> buildUseCaseObservable(GetOrderList_Buyer.Params params) {
        Preconditions.checkNotNull(params);
        return this.orderRepository_buyer.orders_buyer(params.access_token);
    }

    public static final class Params {

        private final String access_token;

        private Params(String access_token) {
            this.access_token = access_token;
        }

        public static GetOrderList_Buyer.Params forOrder(String access_token) {
            return new GetOrderList_Buyer.Params(access_token);
        }
    }
}
