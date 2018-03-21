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
package com.fernandocejas.android10.order.presentation.mapper;

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class PaymentModelDataMapper {

    @Inject
    public PaymentModelDataMapper() {
    }

    public PaymentModel transform(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final PaymentModel paymentModel = new PaymentModel();
        paymentModel.setId(payment.getId());
        paymentModel.setUser_id(payment.getUser_id());
        paymentModel.setCus_profile(payment.getCus_profile());
        paymentModel.setCard_id(payment.getCard_id());
        paymentModel.setBrand(payment.getBrand());
        paymentModel.setLast4(payment.getLast4());
        paymentModel.setCountry(payment.getCountry());
        paymentModel.setExp_month(payment.getExp_month());
        paymentModel.setExp_year(payment.getExp_year());
        paymentModel.setCreated_at(payment.getCreated_at());
        paymentModel.setUpdated_at(payment.getUpdated_at());
        return paymentModel;
    }

    public Collection<PaymentModel> transform(Collection<Payment> payments) {
        Collection<PaymentModel> paymentModels;

        if (payments != null && !payments.isEmpty()) {
            paymentModels = new ArrayList<>();
            for (Payment payment : payments) {
                paymentModels.add(transform(payment));
            }
        } else {
            paymentModels = Collections.emptyList();
        }

        return paymentModels;
    }

    public Payment transform(PaymentModel paymentModel) {
        if (paymentModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Payment payment = new Payment();
        payment.setId(paymentModel.getId());
        payment.setUser_id(paymentModel.getUser_id());
        payment.setCus_profile(paymentModel.getCus_profile());
        payment.setCard_id(paymentModel.getCard_id());
        payment.setBrand(paymentModel.getBrand());
        payment.setLast4(paymentModel.getLast4());
        payment.setCountry(paymentModel.getCountry());
        payment.setExp_month(paymentModel.getExp_month());
        payment.setExp_year(paymentModel.getExp_year());
        payment.setCreated_at(paymentModel.getCreated_at());
        payment.setUpdated_at(paymentModel.getUpdated_at());
        return payment;
    }

    public Collection<Payment> transformToDomain(Collection<PaymentModel> paymentModels) {
        Collection<Payment> payments;

        if (paymentModels != null && !paymentModels.isEmpty()) {
            payments = new ArrayList<>();
            for (PaymentModel paymentModel : paymentModels) {
                payments.add(transform(paymentModel));
            }
        } else {
            payments = Collections.emptyList();
        }

        return payments;
    }
}

