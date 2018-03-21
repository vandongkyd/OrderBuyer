package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.order.domain.repository.TickerRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/8/18.
 */

public class GetListTicker extends UseCase<List<Ticker>,GetListTicker.Params> {
    private final TickerRepository tickerRepository;

    @Inject
    public GetListTicker(TickerRepository tickerRepository,ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor,postExecutionThread);
        this.tickerRepository = tickerRepository;
    }

    @Override
    protected Observable<List<Ticker>> buildUseCaseObservable(GetListTicker.Params params) {
        Preconditions.checkNotNull(params);
        return this.tickerRepository.ticker(params.access_token);
    }
    public static final class Params {

        private final String access_token;

        private Params(String access_token) {
            this.access_token = access_token;
        }

        public static GetListTicker.Params forTicker(String access_token) {
            return new GetListTicker.Params(access_token);
        }
    }
}
