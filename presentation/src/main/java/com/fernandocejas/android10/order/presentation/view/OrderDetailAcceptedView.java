/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface OrderDetailAcceptedView extends LoadDataView {

    void showOrderDetailInView(OrderModel orderModel);

    void renderProductList(Collection<ProductModel> productModels);

    void showDescriptionInView(String description);

    void showDeliveryDateInView(String date);

    void showProviderInView(String avatar, String name, AddressModel address);

    void showOrderUserInView(String avatar, String name, AddressModel address);

    void showStatusInView(String status);

    void showOrderDateInView(String date);

    void showOrderNumberInView(String order_number);

    void onLinkClicked(ProductModel productModel);

    void onBackClicked();

    void onSendMessageClicked();

    void onReceiveClicked();

    void showProductDetail(ProductModel product);

    void showTotalItemsInView(String total);

    void showPriceFeeInView(String price);

    void showSaleTaxFeeInView(String sale_tax);

    void showServiceFeeInView(String service_fee);

    void showBuyerFreeInView(String buyer_fee);

    void showShipFeeInView(String ship_fee);

    void showSurchargeFeeInView(String sub_charge_free);

    void showOtherFeeInView(String other_fee);

    void showTotalInView(String total);

    void showDateTimeSoldInView(String deviverdate);

    void showDeliveryAddressInView(AddressModel address);

}

