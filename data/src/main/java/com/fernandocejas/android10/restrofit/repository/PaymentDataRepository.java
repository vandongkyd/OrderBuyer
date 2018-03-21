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

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.domain.repository.PaymentRepository;
import com.fernandocejas.android10.restrofit.enity.mapper.PaymentEntityDataMapper;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class PaymentDataRepository implements PaymentRepository {

    private final RetrofitHelper retrofitHelper;

    private final PaymentEntityDataMapper paymentEntityDataMapper;

    @Inject
    PaymentDataRepository(
            RetrofitHelper retrofitHelper,
            PaymentEntityDataMapper paymentEntityDataMapper) {
        this.retrofitHelper = retrofitHelper;
        this.paymentEntityDataMapper = paymentEntityDataMapper;
    }

    @Override
    public Observable<List<Payment>> payments(String token) {
        return retrofitHelper
                .getRestApiService()
                .credit_cards("Bearer " + token)
                .map(this.paymentEntityDataMapper::transform);
    }

    @Override
    public Observable<Payment> addPayment(String token, String stripeToken) {
        return retrofitHelper
                .getRestApiService()
                .add_credit_card("Bearer " + token, stripeToken)
                .map(this.paymentEntityDataMapper::transform);
    }

    @Override
    public Observable<Payment> deletePayment(String token, String payment_id) {
        return retrofitHelper
                .getRestApiService()
                .delete_credit_card("Bearer " + token, payment_id)
                .map(this.paymentEntityDataMapper::transform);
    }
}
