package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.domain.repository.PaymentRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 */

public class GetPaymentList extends UseCase<List<Payment>, GetPaymentList.Params> {

    private final PaymentRepository paymentRepository;

    @Inject
    GetPaymentList(PaymentRepository paymentRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.paymentRepository = paymentRepository;
    }

    @Override
    protected Observable<List<Payment>> buildUseCaseObservable(GetPaymentList.Params params) {
        Preconditions.checkNotNull(params);
        return this.paymentRepository.payments(params.access_token);
    }

    public static final class Params {

        private final String access_token;

        private Params(String access_token) {
            this.access_token = access_token;
        }

        public static GetPaymentList.Params forPayment(String access_token) {
            return new GetPaymentList.Params(access_token);
        }
    }
}

