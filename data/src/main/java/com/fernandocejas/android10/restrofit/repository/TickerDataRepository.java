package com.fernandocejas.android10.restrofit.repository;


import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.order.domain.repository.TickerRepository;
import com.fernandocejas.android10.restrofit.enity.PaymentListEntityResponse;
import com.fernandocejas.android10.restrofit.enity.mapper.PaymentEntityDataMapper;
import com.fernandocejas.android10.restrofit.enity.mapper.TickerEnityDataMapper;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/8/18.
 */

@Singleton
public class TickerDataRepository implements TickerRepository{
    private final RetrofitHelper retrofitHelper;
    private final TickerEnityDataMapper tickerDataRepository;
    @Inject
    public TickerDataRepository(RetrofitHelper retrofitHelper,TickerEnityDataMapper tickerDataRepository) {
        this.retrofitHelper = retrofitHelper;
        this.tickerDataRepository = tickerDataRepository;
    }


    @Override
    public Observable<List<Ticker>> ticker(String token) {
        return retrofitHelper
                .getRestApiService()
                .testorder("Bearer " + token)
                .map(this.tickerDataRepository:: transform);
    }



    @Override
    public Observable<Ticker> addTicker(String token, String stripeToken) {
        return null;
    }

    @Override
    public Observable<Ticker> deleteTicker(String token, String payment_id) {
        return null;
    }
}
