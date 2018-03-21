/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.order.presentation.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.URLUtil;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.fernandocejas.android10.BuildConfig;
import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.Image;
import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.domain.Setting;
import com.fernandocejas.android10.order.domain.SettingByCountry;
import com.fernandocejas.android10.order.domain.interactor.CreateOrder;
import com.fernandocejas.android10.order.presentation.mapper.AddressModelDataMapper;
import com.fernandocejas.android10.order.presentation.mapper.ProductModelDataMapper;
import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.utils.AWSHelper;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.OrderView;
import com.fernandocejas.android10.order.presentation.view.activity.OrderActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class OrderPresenter implements Presenter {

    @Inject
    AWSHelper awsHelper;

    private OrderView orderView;

    private final CreateOrder createOrderUseCase;
    private final ProductModelDataMapper productModelDataMapper;
    private final AddressModelDataMapper addressModelDataMapper;

    private SettingByCountry settingByCountry;
    private Address address;

    private float tax;
    private float tax_in_percent = 0.0f;
    private float amount;
    private int quantity;
    private String currency;

    @Inject
    public OrderPresenter(ProductModelDataMapper productModelDataMapper,
                          AddressModelDataMapper addressModelDataMapper,
                          CreateOrder createOrderUseCase) {
        this.productModelDataMapper = productModelDataMapper;
        this.addressModelDataMapper = addressModelDataMapper;
        this.createOrderUseCase = createOrderUseCase;

    }

    public void setView(@NonNull OrderView view) {
        this.orderView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.createOrderUseCase.dispose();
        this.orderView = null;
    }

    public void navigationToProduct() {
        if (this.orderView.activity() instanceof OrderActivity) {
            ((OrderActivity) orderView.activity()).navigateToProduct();
        }
    }

    public void navigateToOrderList() {
        if (this.orderView.activity() instanceof OrderActivity) {
            ((OrderActivity) orderView.activity()).navigateToOrderList();
        }
    }

    private void navigationToAddAddress() {
        if (this.orderView.activity() instanceof OrderActivity) {
            ((OrderActivity) orderView.activity()).navigateToAddAddress();
        }
    }

    public void submitOrder(String description) {
        this.hideViewRetry();
        this.showViewLoading();
        this.submit(description);
    }

    public void gotToAddAddress() {
        navigationToAddAddress();
    }

    private void showViewLoading() {
        this.orderView.showLoading();
    }

    private void hideViewLoading() {
        this.orderView.hideLoading();
    }

    private void showViewRetry() {
        this.orderView.showRetry();
    }

    private void hideViewRetry() {
        this.orderView.hideRetry();
    }

    private void showSubmitSuccess() {
        this.orderView.showSubmitSuccess();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.orderView.context(),
                errorBundle.getException());
        this.orderView.showError(errorMessage);
    }

    public void loadProducts() {
        getProductsFromLocal();
    }

    public void calculatePrice() {
        Collection<Product> products = new ArrayList<>(Constants.PRODUCTS.values());
        showPriceIView(products);
    }

    private void getProductsFromLocal() {
        Collection<Product> products = new ArrayList<>(Constants.PRODUCTS.values());
        //
        showProductsInView(products);
        showPriceIView(products);
    }

    private void submit(String description) {
        String token = PreferencesUtility.getInstance(orderView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        //
        uploadImageToS3();
        //todo: create Order
        Order order = new Order();
        order.setTax(tax + "");
        order.setAmount(amount + "");
        order.setQuantity(quantity + "");
        order.setProducts(new ArrayList<>(Constants.PRODUCTS.values()));
        order.setServicefee("0.0");
        order.setCountry(Constants.COUNTRY);
        order.setCurrency(currency);
        order.setAddress(address);
        order.setDescription(description);
        createOrderUseCase.execute(new CreateOrderObserver(), CreateOrder.Params.forCreateOrder(token, order));
    }

    private void uploadImageToS3() {
        for (Product product : Constants.PRODUCTS.values()) {
            Collection<Image> images = product.getImages();
            if (images != null && !images.isEmpty()) {
                for (Image image : images) {
                    if (!URLUtil.isNetworkUrl(image.getImage())) {
                        //upload image to S3
                        try {
                            String filePath = image.getImage();
                            String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
                            image.setImage(BuildConfig.AVATAR_HOST + filename);
                            //
                            uploadPhotoToAWS(this.orderView.context(), filename, new File(filePath));
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        }
    }

    private void uploadPhotoToAWS(Context context, String filename, File file) {
        Log.d("s3_upload_path", BuildConfig.AVATAR_HOST + filename);
        awsHelper.getTransferUtility(context).upload(BuildConfig.BUCKET_NAME, filename, file)
                .setTransferListener(new TransferListener() {
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        Log.d("s3", "onStateChanged");
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        Log.d("s3", "onProgressChanged");
                    }

                    @Override
                    public void onError(int id, Exception ex) {
                        Log.d("s3", "onError");
                    }
                });
    }

    private void bindAddressesToView() {
        this.orderView.renderAddresses(addressModelDataMapper.transform(settingByCountry.getAddresses()));
    }

    public void addAddress(Address address) {
        try {
            OrderPresenter.this.setAddress(addressModelDataMapper.transform(address));
            this.settingByCountry.getAddresses().add(address);
            this.bindAddressesToView();
            //select address to newest one
            try {
                int size = this.settingByCountry.getAddresses().size();
                List<Address> adList = (List) this.settingByCountry.getAddresses();
                for (int i = 0; i < size; i++) {
                    Address a = adList.get(i);
                    if (a.getId().equals(address.getId())) {
                        this.orderView.selectAddressInView(i + 1);
                        break;
                    }
                }
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
        }
    }

    private void showPriceIView(Collection<Product> products) {
        amount = 0;
        quantity = 0;
        currency = null;
        String currencySymbol = "";
        for (Product product : products) {
            try {
                float p = Float.valueOf(product.getPrice());
                int q = Integer.valueOf(product.getQuantity());
                quantity += q;
                amount += p * q;
                //
                if (currency == null) {
                    currency = product.getCurrency();
                    currencySymbol = Utils.getCurrencySymbol(currency);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //
        tax = amount * tax_in_percent / 100;
        this.orderView.showSalesTaxTitleInView(tax_in_percent + "");
        this.orderView.showTotalItemsInView(quantity + "");
        this.orderView.showPriceInView(currencySymbol + " " + Utils.formatDecimal(amount+""));
        this.orderView.showSalesTaxInView(currencySymbol + " " + Utils.formatDecimal(tax+""));
        this.orderView.showTotalInView(currencySymbol + " " + Utils.formatDecimal((amount + tax)+""));
        this.orderView.showSubmitInformation(quantity, currencySymbol, Utils.formatDecimal((amount + tax)+""));


    }

    private void showProductsInView(Collection<Product> products) {
        final Collection<ProductModel> productModelList =
                this.productModelDataMapper.transform(products);
        this.orderView.renderProducts((List) productModelList);
    }

    public void setSettingByCountry(SettingByCountry settingByCountry) {
        this.settingByCountry = settingByCountry;
        //calculate tax
        Collection<Setting> settings = this.settingByCountry.getSetting();
        for (Setting setting : settings) {
            if (setting.getCode().equals("tax")) {
                tax_in_percent = Float.parseFloat(setting.getValues());
            }
        }
        //bind addresses
        bindAddressesToView();
    }

    public void setAddress(AddressModel address) {
        if (address != null && !address.isIs_header()) {
            this.address = addressModelDataMapper.transform(address);
            this.orderView.showAddressInView(address.getAddress());
        }
    }

    private final class CreateOrderObserver extends DefaultObserver<Order> {

        @Override
        public void onComplete() {
            OrderPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            OrderPresenter.this.hideViewLoading();
            OrderPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            OrderPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Order order) {
            if (order != null) {
                OrderPresenter.this.showSubmitSuccess();
                OrderPresenter.this.navigateToOrderList();
            }
        }
    }
}
