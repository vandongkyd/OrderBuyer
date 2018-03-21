package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.domain.Ticker;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/8/18.
 */

public interface TickerRepository {
    Observable<List<Ticker>> ticker(String token);
    Observable<Ticker> addTicker(String token, String stripeToken);

    Observable<Ticker> deleteTicker(String token, String payment_id);
}
