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

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.fernandocejas.android10.order.domain.Image;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.domain.SettingByCountry;
import com.fernandocejas.android10.order.domain.interactor.GetSettingByCountry;
import com.fernandocejas.android10.order.presentation.mapper.ProductModelDataMapper;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.ProductListView;
import com.fernandocejas.android10.order.presentation.view.activity.ProductListActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ProductListPresenter implements Presenter {

    public static final int REQUEST_ADD_PRODUCT_FROM_URL = 101;

    private ProductListView productListView;
    private final ProductModelDataMapper productModelDataMapper;
    private final GetSettingByCountry getSettingByCountryUseCase;
    private int currentPosition;
    private String currentLink;

    @Inject
    public ProductListPresenter(ProductModelDataMapper productModelDataMapper,
                                GetSettingByCountry getSettingByCountry) {
        this.productModelDataMapper = productModelDataMapper;
        this.getSettingByCountryUseCase = getSettingByCountry;
    }

    public void setView(@NonNull ProductListView view) {
        this.productListView = view;
    }

    @Override
    public void resume() {
        if (Constants.PRODUCTS.isEmpty()) {
            navigateToOrderList();
            return;
        }
        this.showProductsCollectionInView(Constants.PRODUCTS);
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getSettingByCountryUseCase.dispose();
        this.productListView = null;
    }

    private void showViewLoading() {
        this.productListView.showLoading();
    }

    private void hideViewLoading() {
        this.productListView.hideLoading();
    }

    private void showViewRetry() {
        this.productListView.showRetry();
    }

    private void hideViewRetry() {
        this.productListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.productListView.context(),
                errorBundle.getException());
        this.productListView.showError(errorMessage);
    }

    public void setProduct(Product product) {
        if (Constants.PRODUCTS == null) {
            Constants.PRODUCTS = new HashMap<>();
        }
        Product p = Constants.PRODUCTS.get(product.getLink());
        if (p != null) {
            int q = 1;
            try {
                q = Integer.valueOf(p.getQuantity());
                q++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            p.setQuantity(q + "");
        } else {
            Constants.PRODUCTS.put(product.getLink(), product);
        }
    }

    public void changeQuantity(
            String link, int increase, int stock, int position) {
        Product product = Constants.PRODUCTS.get(link);
        int min = 1, max = stock;
        int q = min;
        try {
            q = Integer.valueOf(product.getQuantity());
            if (increase < 0 && q <= min) {
                //this will set result to negative number or zero number
            } else {
                if (increase > 0 && q >= max) {
                    //out of stock
                } else {
                    q += increase;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        product.setQuantity(q + "");
        this.productListView.reRenderProductInList(productModelDataMapper.transform(product), position);
    }

    public void changeWeight(String link, String w) {
        Product product = Constants.PRODUCTS.get(link);
        if(product == null){
            return;
        }
        product.setWeight(w);
    }

    public void changePrice(String link, String p) {
        Product product = Constants.PRODUCTS.get(link);
        if(product == null){
            return;
        }
        product.setPrice(p);
    }

    public void loadSetting() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getSetting();
    }

    private void showProductsCollectionInView(HashMap<String, Product> productHashMap) {
        Collection<Product> products = new ArrayList<>(productHashMap.values());
        this.productListView.renderProductList(productModelDataMapper.transform(products));
    }

    public void navigateToOrderList() {
        if (this.productListView.activity() instanceof ProductListActivity) {
            ((ProductListActivity) productListView.activity()).navigateToOrderList();
        }
    }

    public void navigationTopWebsiteForResult(Fragment fragment, String url) {
        if (this.productListView.activity() instanceof ProductListActivity) {
            ((ProductListActivity) productListView.activity()).navigateToTopWebsiteForResult(url, fragment, REQUEST_ADD_PRODUCT_FROM_URL);
        }
    }

    private void navigationToOrder(SettingByCountry settingByCountry) {
        if (this.productListView.activity() instanceof ProductListActivity) {
            ((ProductListActivity) productListView.activity()).navigateToOrder(settingByCountry);
        }
    }

    public void addUrl(Fragment fragment, String url) {
        this.navigationTopWebsiteForResult(fragment, url);
    }

    public void goToOrder() {
        loadSetting();
    }

    private void getSetting() {
        String token = PreferencesUtility.getInstance(productListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        getSettingByCountryUseCase.execute(new GetSettingObserver(), GetSettingByCountry.Params.forSetting(token, Constants.COUNTRY.getIso()));
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void addPhotoToProduct(String path) {
        Product product = Constants.PRODUCTS.get(currentLink);
        // create new image
        Image image = new Image();
        image.setImage(path);
        // add image
        product.getImages().add(image);
    }

    public void setCurrentLink(String currentLink) {
        this.currentLink = currentLink;
    }

    private final class GetSettingObserver extends DefaultObserver<SettingByCountry> {

        @Override
        public void onComplete() {
            ProductListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            ProductListPresenter.this.hideViewLoading();
            ProductListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ProductListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(SettingByCountry settingByCountry) {
            if (settingByCountry != null) {
                ProductListPresenter.this.navigationToOrder(settingByCountry);
            }
        }
    }

}
