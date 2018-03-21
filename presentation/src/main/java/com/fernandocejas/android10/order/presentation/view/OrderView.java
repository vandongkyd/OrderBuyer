/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.interactor.AddAddress;
import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;
import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface OrderView extends LoadDataView {

    void renderProducts(List<ProductModel> productModelList);

    void renderAddresses(Collection<AddressModel> addressModels);

    void onSubmitClicked();

    void onLinkClicked(ProductModel productModel);

    void showTotalItemsInView(String total_items);

    void showTotalInView(String total);

    void showPriceInView(String price);

    void showSalesTaxInView(String tax);

    void showAddressInView(String address);

    void showSubmitInformation(int quantity, String currencySymbol, String money);

    void showSalesTaxTitleInView(String tax);

    void onBackClicked();

    void showSubmitSuccess();

    void onChoiceAddressClicked();

    void onAddressAdded(Address address);

    void selectAddressInView(int position);
}

