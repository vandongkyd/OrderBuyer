/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.view.adapter.ProductAdapter;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface ProductAdapterView {

    void showNameProductInView(ProductAdapter.ItemBaseViewHolder viewHolder, String name);

    void showThumbnailInView(ProductAdapter.ItemBaseViewHolder viewHolder, String url);

    void showLinkInView(ProductAdapter.ItemBaseViewHolder viewHolder, String link);

    void showPriceInView(ProductAdapter.ItemBaseViewHolder viewHolder, String price);

    void showQuantityInView(ProductAdapter.ItemBaseViewHolder viewHolder, String quantity);

    void onItemClicked(ProductModel productModel);

    void onItemRemoved(ProductModel productModel, int position);

    void onLinkClicked(ProductModel productModel);
}

