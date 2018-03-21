package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.TestOrder;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link TestOrder} related data.
 */

public interface TestOrderReposity {
    /**
     * Get an {@link Observable} which will emit a List of {@link Order}.
     */
    Observable<List<TestOrder>> orders(String token, String offset);

    /**
     * Get an {@link Observable} which will emit a {@link TestOrder}.
     *
     * @param order_id The order id used to retrieve order data.
     */
    Observable<TestOrder> order(String token, final String order_id);

    Observable<TestOrder> create(String token, final Order order);

}

