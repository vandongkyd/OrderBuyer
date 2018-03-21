/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.view.adapter.ProductDetailAdapter;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface ProductDetailAdapterView {

    void renderImageList(ProductDetailAdapter.ProductViewHolder viewHolder, Collection<ImageModel> imageModels);

    void showNameProductInView(ProductDetailAdapter.ProductViewHolder viewHolder, String name);

    void showDescriptionInView(ProductDetailAdapter.ProductViewHolder viewHolder, String description);

    void showLinkInView(ProductDetailAdapter.ProductViewHolder viewHolder, String link);

    void showQuantityInView(ProductDetailAdapter.ProductViewHolder viewHolder, String quantity, int stock);

    void showWeightInView(ProductDetailAdapter.ProductViewHolder viewHolder, String weight);

    void showPriceInView(ProductDetailAdapter.ProductViewHolder viewHolder, String price);

    void showWeightUnitInView(ProductDetailAdapter.ProductViewHolder viewHolder, String unit);

    void showCurrencySymbolInView(ProductDetailAdapter.ProductViewHolder viewHolder, String symbol);

    void onPlusQuantityClicked(ProductModel productModel, int position);

    void onMinusQuantityClicked(ProductModel productModel, int position);

    void onWeightInputChanged(ProductDetailAdapter.ProductViewHolder viewHolder, ProductModel productModel);

    void onPriceInputChanged(ProductDetailAdapter.ProductViewHolder viewHolder, ProductModel productModel);

    void onLinkClicked(ProductModel productModel);

    void onPickPhotoClicked(ProductModel productModel, int position);

    void onShowSlideshowClicked(ProductModel productModel);

}

