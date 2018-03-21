/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.view.adapter.OrderAdapter;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface OrderAdapterView {

    void renderOfferList(OrderAdapter.OrderViewHolder viewHolder, Collection<OfferModel> offerModels, String status, String provider_id);

    void renderImageList(OrderAdapter.OrderViewHolder viewHolder, Collection<ImageModel> imageModels);

    void showDescriptionInView(OrderAdapter.OrderViewHolder viewHolder, String description);

    void showShippingInfoInView(OrderAdapter.OrderViewHolder viewHolder, String deliveryFrom, String deliveryTo);

    void showPriceInView(OrderAdapter.OrderViewHolder viewHolder, String price);

    void showOrderDateInView(OrderAdapter.OrderViewHolder viewHolder, String date_ago);

    void showShipFeeInView(OrderAdapter.OrderViewHolder viewHolder, String price, String status);

    void showDeliveryDateInView(OrderAdapter.OrderViewHolder viewHolder, String date, String status);

    void showStatusInView(OrderAdapter.OrderViewHolder viewHolder, String status);

    void onOrderItemClicked(OrderModel orderModel);
}

