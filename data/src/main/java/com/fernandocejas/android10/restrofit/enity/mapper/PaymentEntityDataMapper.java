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
package com.fernandocejas.android10.restrofit.enity.mapper;

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.restrofit.enity.PaymentEntity;
import com.fernandocejas.android10.restrofit.enity.PaymentEntityResponse;
import com.fernandocejas.android10.restrofit.enity.PaymentListEntityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PaymentEntityDataMapper {

    @Inject
    PaymentEntityDataMapper() {
    }

    public Payment transform(PaymentEntity paymentEntity) {
        Payment payment = null;
        if (paymentEntity != null) {
            payment = new Payment();
            payment.setId(paymentEntity.getId());
            payment.setUser_id(paymentEntity.getUser_id());
            payment.setCus_profile(paymentEntity.getCus_profile());
            payment.setCard_id(paymentEntity.getCard_id());
            payment.setBrand(paymentEntity.getBrand());
            payment.setLast4(paymentEntity.getLast4());
            payment.setCountry(paymentEntity.getCountry());
            payment.setExp_month(paymentEntity.getExp_month());
            payment.setExp_year(paymentEntity.getExp_year());
            payment.setCreated_at(paymentEntity.getCreated_at());
            payment.setUpdated_at(paymentEntity.getUpdated_at());
        }
        return payment;
    }

    public PaymentEntity transform(Payment payment) {
        PaymentEntity paymentEntity = null;
        if (payment != null) {
            paymentEntity = new PaymentEntity();
            paymentEntity.setId(payment.getId());
            paymentEntity.setUser_id(payment.getUser_id());
            paymentEntity.setCus_profile(payment.getCus_profile());
            paymentEntity.setCard_id(payment.getCard_id());
            paymentEntity.setBrand(payment.getBrand());
            paymentEntity.setLast4(payment.getLast4());
            paymentEntity.setCountry(payment.getCountry());
            paymentEntity.setExp_month(payment.getExp_month());
            paymentEntity.setExp_year(payment.getExp_year());
            paymentEntity.setCreated_at(payment.getCreated_at());
            paymentEntity.setUpdated_at(payment.getUpdated_at());
        }
        return paymentEntity;
    }

    public List<Payment> transform(Collection<PaymentEntity> paymentEntities) {
        final List<Payment> payments = new ArrayList<>();
        for (PaymentEntity paymentEntity : paymentEntities) {
            final Payment payment = transform(paymentEntity);
            if (payment != null) {
                payments.add(payment);
            }
        }
        return payments;
    }

    public List<PaymentEntity> transformToEntity(Collection<Payment> payments) {
        final List<PaymentEntity> paymentEntities = new ArrayList<>();
        for (Payment payment : payments) {
            final PaymentEntity paymentEntity = transform(payment);
            if (paymentEntity != null) {
                paymentEntities.add(paymentEntity);
            }
        }
        return paymentEntities;
    }

    public List<Payment> transform(PaymentListEntityResponse paymentListEntityResponse) throws Exception {
        List<Payment> paymentList = null;
        if (paymentListEntityResponse != null) {
            if (paymentListEntityResponse.status() == false) {
                throw new Exception(paymentListEntityResponse.message());
            }
            paymentList = transform(paymentListEntityResponse.data());
        }
        return paymentList;
    }

    public Payment transform(PaymentEntityResponse paymentEntityResponse) throws Exception {
        Payment payment = null;
        if (paymentEntityResponse != null) {
            if (paymentEntityResponse.status() == false) {
                throw new Exception(paymentEntityResponse.message());
            }
            payment = transform(paymentEntityResponse.data());
        }
        return payment;
    }

}
