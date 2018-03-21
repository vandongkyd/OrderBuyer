package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Payment;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 *
 */

public interface PaymentRepository {

    Observable<List<Payment>> payments(String token);

    Observable<Payment> addPayment(String token, String stripeToken);

    Observable<Payment> deletePayment(String token, String payment_id);
}
