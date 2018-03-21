/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.view.adapter.PaymentSpinnerAdapter;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface PaymentSpinnerAdapterView {

    void showLast4InView(PaymentSpinnerAdapter.ItemBaseViewHolder viewHolder, String last4);

    void showIconInView(PaymentSpinnerAdapter.ItemBaseViewHolder viewHolder, String brand);

}

