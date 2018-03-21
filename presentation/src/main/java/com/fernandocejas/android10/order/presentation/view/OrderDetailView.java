/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface OrderDetailView extends LoadDataView {

    void showOrderDetailInView(OrderModel orderModel);

    void renderProductList(Collection<ProductModel> productModels);

    void renderOfferList(Collection<OfferModel> offerModels);

    void renderImageList(Collection<ImageModel> imageModels);

    void showDescriptionInView(String description);

    void showShippingInfoInView(String deliveryFrom, String deliveryTo);

    void showPriceInView(String price);

    void showOrderDateInView(String date);

    void showOrderNumberInView(String order_number);

    void onLinkClicked(ProductModel productModel);

    void onSendMessageClicked(OfferModel offerModel);

    void onConfirmClicked(OfferModel offerModel);

    void onBackClicked();

    void showProductDetail(ProductModel product);

}

