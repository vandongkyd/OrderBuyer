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

public class AddTicker extends UseCase<Ticker, AddTicker.Params> {

    private final TickerRepository tickerRepository;

    @Inject
    AddTicker(TickerRepository tickerRepository, ThreadExecutor threadExecutor,
               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.tickerRepository = tickerRepository;
    }

    @Override
    protected Observable<Ticker> buildUseCaseObservable(AddTicker.Params params) {
        Preconditions.checkNotNull(params);
        return this.tickerRepository.addTicker(params.access_token, params.stripe_token);
    }

    public static final class Params {

        private final String access_token;
        private final String stripe_token;

        private Params(String access_token, String stripe_token) {
            this.access_token = access_token;
            this.stripe_token = stripe_token;
        }

        public static AddTicker.Params forPayment(String access_token, String stripe_token) {
            return new AddTicker.Params(access_token, stripe_token);
        }
    }
}