package com.fernandocejas.android10.order.domain.repository_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Order_Buyer;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/14/18.
 */

public interface OrderRepository_Buyer {
    /**
     * Get an {@link Observable} which will emit a List of {@link Order_Buyer}.
     */
    Observable<List<Order_Buyer>> orders_buyer(String token);

    /**
     * Get an {@link Observable} which will emit a {@link Order_Buyer}.
     *
     */

    Observable<List<Order_Buyer>> my_orders(String token);

    Observable<Order_Buyer> create_buyer(String token, final Order_Buyer order);
}
