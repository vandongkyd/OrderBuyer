package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.domain.repository.PaymentRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 */

public class AddPayment extends UseCase<Payment, AddPayment.Params> {

    private final PaymentRepository paymentRepository;

    @Inject
    AddPayment(PaymentRepository paymentRepository, ThreadExecutor threadExecutor,
               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.paymentRepository = paymentRepository;
    }

    @Override
    protected Observable<Payment> buildUseCaseObservable(AddPayment.Params params) {
        Preconditions.checkNotNull(params);
        return this.paymentRepository.addPayment(params.access_token, params.stripe_token);
    }

    public static final class Params {

        private final String access_token;
        private final String stripe_token;

        private Params(String access_token, String stripe_token) {
            this.access_token = access_token;
            this.stripe_token = stripe_token;
        }

        public static AddPayment.Params forPayment(String access_token, String stripe_token) {
            return new AddPayment.Params(access_token, stripe_token);
        }
    }
}

