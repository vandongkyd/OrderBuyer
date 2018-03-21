package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.TestOrder;
import com.fernandocejas.android10.order.domain.repository.OrderRepository;
import com.fernandocejas.android10.order.domain.repository.TestOrderReposity;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/7/18.
 */

public class TestGetListOrder extends UseCase<List<TestOrder>, TestGetListOrder.Params> {

    private final TestOrderReposity orderRepository;

    @Inject
    TestGetListOrder(TestOrderReposity orderRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.orderRepository = orderRepository;
    }

    @Override
    protected Observable<List<TestOrder>> buildUseCaseObservable(TestGetListOrder.Params params) {
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

        public static TestGetListOrder.Params forOrder(String access_token, String offset) {
            return new TestGetListOrder.Params(access_token, offset);
        }
    }
}
