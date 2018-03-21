package com.fernandocejas.android10.order.presentation.mapper;

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.TestOrder;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.model.TestOrderModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by vandongluong on 3/7/18.
 */

public class TestOrderModelMapper {

    /**
     * Transform a {@link TestOrder} into an {@link TestOrderModel}.
     *
     * @param order Object to be transformed.
     * @return {@link TestOrderModel}.
     */
    public TestOrderModel transform(TestOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
//        private String id;
//        private String name;
//        private String symbol;
//        private String rank;
//        private String price_usd;
//        private String price_btc;
//        private String h_volume_usd;
//        private String market_cap_usd;
//        private String available_supply;
//        private String total_supply;
//        private String max_supply;
//        private String percent_change_1h;
//        private String percent_change_24h;
//        private String percent_change_7d;
//        private String last_updated;
        final TestOrderModel orderModel = new TestOrderModel();
        orderModel.setId(order.getId());
        orderModel.setName(order.getName());
        orderModel.setSymbol(order.getSymbol());
        orderModel.setRank(order.getRank());
        orderModel.setPrice_usd(order.getPrice_usd());
        orderModel.setPrice_btc(order.getPrice_btc());
        orderModel.setH_volume_usd(order.getH_volume_usd());
        orderModel.setMarket_cap_usd(order.getMarket_cap_usd());
        orderModel.setAvailable_supply(order.getAvailable_supply());
        orderModel.setTotal_supply(order.getTotal_supply());
        orderModel.setMax_supply(order.getMax_supply());
        orderModel.setPercent_change_1h(order.getPercent_change_1h());
        orderModel.setPercent_change_24h(order.getPercent_change_24h());
        orderModel.setPercent_change_7d(order.getPercent_change_7d());
        orderModel.setLast_updated(order.getLast_updated());
        return orderModel;
    }

    /**
     * Transform a Collection of {@link TestOrder} into a Collection of {@link TestOrderModel}.
     *
     * @param ordersCollection Objects to be transformed.
     * @return List of {@link TestOrderModel}.
     */
    public Collection<TestOrderModel> transformto(Collection<TestOrder> ordersCollection) {
        Collection<TestOrderModel> orderModelsCollection;

        if (ordersCollection != null && !ordersCollection.isEmpty()) {
            orderModelsCollection = new ArrayList<>();
            for (TestOrder order : ordersCollection) {
                orderModelsCollection.add(transform(order));
            }
        } else {
            orderModelsCollection = Collections.emptyList();
        }

        return orderModelsCollection;
    }
}
