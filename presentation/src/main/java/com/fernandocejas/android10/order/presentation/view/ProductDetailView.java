/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface ProductDetailView extends ProductDetailAdapterView, LoadDataView {

    void showOrderNumberInView(String order_number);

    void showProductInView(ProductModel productModel);

    void onBackClicked();

    void onShowSlideshowClicked();

    void onLinkClicked();

}

