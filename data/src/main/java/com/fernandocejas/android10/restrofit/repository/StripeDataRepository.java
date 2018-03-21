/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.restrofit.repository;

import android.content.Context;

import com.fernandocejas.android10.order.domain.Token;
import com.fernandocejas.android10.order.domain.repository.StripeRepository;
import com.fernandocejas.android10.restrofit.enity.mapper.TokenStripeDataMapper;
import com.fernandocejas.android10.sample.data.BuildConfig;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class StripeDataRepository implements StripeRepository {


    private final TokenStripeDataMapper tokenStripeDataMapper;

    private final Stripe stripe;

    @Inject
    StripeDataRepository(Context context,
                         TokenStripeDataMapper tokenStripeDataMapper) {
        this.tokenStripeDataMapper = tokenStripeDataMapper;
        this.stripe = new Stripe(context);
        this.stripe.setDefaultPublishableKey(BuildConfig.STRIPE_PULISHABLE_KEY);
    }

    @Override
    public Observable<Token> createToken(String card_number, int exp_month, int exp_year, String cvc) {
        Card card = new Card(card_number, exp_month, exp_year, cvc);
        return Observable.fromCallable(
                () -> {
                    // When executed, this method will conduct i/o on whatever thread it is run on
                    return stripe.createTokenSynchronous(card);
                }).map(this.tokenStripeDataMapper::transform);

    }
}
