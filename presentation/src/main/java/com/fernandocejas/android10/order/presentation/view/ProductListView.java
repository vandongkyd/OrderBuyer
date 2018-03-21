/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.presentation.model.EventModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface ProductListView extends LoadDataView {

    void renderProductList(Collection<ProductModel> productCollection);

    void reRenderProductInList(ProductModel productModel, int position);

    void onAddMoreClicked();

    void onNextClicked();

    void onBackClicked();

    void showAddUrlDialog();

    void onShowSlideshowClicked(ProductModel productModel);

}

