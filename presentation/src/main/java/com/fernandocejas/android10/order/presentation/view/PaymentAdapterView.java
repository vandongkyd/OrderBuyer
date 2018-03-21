/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.view.adapter.PaymentAdapter;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface PaymentAdapterView {

    void showLast4InView(PaymentAdapter.ItemBaseViewHolder viewHolder, String last4);

    void showIconInView(PaymentAdapter.ItemBaseViewHolder viewHolder, String brand);

}

