package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Token;

import io.reactivex.Observable;

/**
 * Created by jerry on Jan-16-18.
 */

public interface StripeRepository {

    Observable<Token> createToken(String card_number, int exp_month, int exp_year, String cvc);

}
