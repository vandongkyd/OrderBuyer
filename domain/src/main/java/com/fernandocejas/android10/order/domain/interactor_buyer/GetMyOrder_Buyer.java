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
 * Created by vandongluong on 3/19/18.
 */

public class GetMyOrder_Buyer extends UseCase<List<Order_Buyer>, GetMyOrder_Buyer.Params> {

    private final OrderRepository_Buyer orderRepository_buyer;

    @Inject
    GetMyOrder_Buyer(OrderRepository_Buyer orderRepository_buyer, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.orderRepository_buyer = orderRepository_buyer;
    }

    @Override
    protected Observable<List<Order_Buyer>> buildUseCaseObservable(GetMyOrder_Buyer.Params params) {
        Preconditions.checkNotNull(params);
        return this.orderRepository_buyer.my_orders(params.access_token);
    }

    public static final class Params {

        private final String access_token;

        private Params(String access_token) {
            this.access_token = access_token;
        }

        public static GetMyOrder_Buyer.Params forMyOrder(String access_token) {
            return new GetMyOrder_Buyer.Params(access_token);
        }
    }
}
