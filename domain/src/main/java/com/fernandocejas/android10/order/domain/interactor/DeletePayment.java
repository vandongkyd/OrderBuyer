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

public class DeletePayment extends UseCase<Payment, DeletePayment.Params> {

    private final PaymentRepository paymentRepository;

    @Inject
    DeletePayment(PaymentRepository paymentRepository, ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.paymentRepository = paymentRepository;
    }

    @Override
    protected Observable<Payment> buildUseCaseObservable(DeletePayment.Params params) {
        Preconditions.checkNotNull(params);
        return this.paymentRepository.deletePayment(params.access_token, params.payment_id);
    }

    public static final class Params {

        private final String access_token;
        private final String payment_id;

        private Params(String access_token, String payment_id) {
            this.access_token = access_token;
            this.payment_id = payment_id;
        }

        public static DeletePayment.Params forPayment(String access_token, String payment_id) {
            return new DeletePayment.Params(access_token, payment_id);
        }
    }
}

