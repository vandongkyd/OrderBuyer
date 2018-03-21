/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.model.RateModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;
import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface AcceptedView extends LoadDataView {

    void showOfferInView(OfferModel offer);

    void showLogoInView(String url);

    void showNameInView(String name);

    void showRatingInView(RateModel rate);

    void showAddressInView(String address);

    void showDeliveryDateInView(String date);

    void renderPaymentList(Collection<PaymentModel> paymentModels);

    void showQuantityInView(String quantity);

    void showAmountInView(String amount);

    void showWeightInView(String weight);

    void showSaleTaxInView(String sale_tax);

    void showServiceFeeInView(String service_fee);

    void showBuyFeeInView(String buy_fee);

    void showShipFeeInView(String ship_fee);

    void showSurfaceFeeInView(String surface_fee);

    void showOtherFeeInView(String other_fee);

    void showTotalFeeInView(String total_fee);

    void onChoicePaymentClicked();

    void showPaymentList();

    void onBackClicked();

    void onItemPaymentSelected(PaymentModel paymentModel);

    void onConFirmClicked();

}

