/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface PaymentListView extends LoadDataView {

    void renderPaymentMethodList(Collection<PaymentModel> paymentModels);

    void onAddPaymentClicked();

    void onBackClicked();

    void onSavePaymentInDialogClicked(String card_number, int exp_month, int exp_year, String cvc);

    void onPaymentRemoveClicked(PaymentModel paymentModel, int position);

    void showDeleteSuccess(int position);
}

