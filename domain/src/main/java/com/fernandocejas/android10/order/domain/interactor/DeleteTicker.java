package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.order.domain.repository.PaymentRepository;
import com.fernandocejas.android10.order.domain.repository.TickerRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/8/18.
 */

public class DeleteTicker extends UseCase<Ticker, DeleteTicker.Params> {

    private final TickerRepository tickerRepository;

    @Inject
    DeleteTicker(TickerRepository tickerRepository, ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.tickerRepository = tickerRepository;
    }

    @Override
    protected Observable<Ticker> buildUseCaseObservable(DeleteTicker.Params params) {
        Preconditions.checkNotNull(params);
        return this.tickerRepository.deleteTicker(params.access_token, params.payment_id);
    }

    public static final class Params {

        private final String access_token;
        private final String payment_id;

        private Params(String access_token, String payment_id) {
            this.access_token = access_token;
            this.payment_id = payment_id;
        }

        public static DeleteTicker.Params forTicker(String access_token, String payment_id) {
            return new DeleteTicker.Params(access_token, payment_id);
        }
    }
}