/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.view.adapter.AddressAdapter;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface AddressAdapterView {

    void showAddressInView(AddressAdapter.AddressViewHolder viewHolder, AddressModel addressModel);

}

