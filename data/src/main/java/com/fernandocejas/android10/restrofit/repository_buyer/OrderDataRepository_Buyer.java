package com.fernandocejas.android10.restrofit.repository_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Order_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.OrderRepository_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer.OrderEntityDataMapper_Buyer;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper_Buyer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/14/18.
 */

@Singleton
public class OrderDataRepository_Buyer implements OrderRepository_Buyer{

    private final RetrofitHelper_Buyer retrofitHelper;
    private final OrderEntityDataMapper_Buyer orderEntityDataMapper_buyer;

    /**
     * Constructs a {@link OrderRepository_Buyer}.
     *  @param retrofitHelper
     * @param orderEntityDataMapper_buyer
     */
    @Inject
    OrderDataRepository_Buyer(RetrofitHelper_Buyer retrofitHelper,
                              OrderEntityDataMapper_Buyer orderEntityDataMapper_buyer){

        this.retrofitHelper = retrofitHelper;
        this.orderEntityDataMapper_buyer = orderEntityDataMapper_buyer;
    }


    @Override
    public Observable<List<Order_Buyer>> orders_buyer(String token) {
        return retrofitHelper
                .getRestApiService()
                .order_buyer("Bearer " + token)
                .map(this.orderEntityDataMapper_buyer::transform);
    }

    @Override
    public Observable<List<Order_Buyer>> my_orders(String token) {
        return retrofitHelper
                .getRestApiService()
                .my_order_buyer("Bearer " + token)
                .map(this.orderEntityDataMapper_buyer::transform);
    }

    @Override
    public Observable<Order_Buyer> create_buyer(String token, Order_Buyer order) {
        return null;
    }
}
