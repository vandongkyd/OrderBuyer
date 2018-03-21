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
import android.webkit.URLUtil;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.domain.interactor.ParseProductFromUrl;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.TopWebsiteView;
import com.fernandocejas.android10.order.presentation.view.activity.TopWebsiteActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class TopWebsitePresenter implements Presenter {

    private TopWebsiteView topWebsiteView;

    private final ParseProductFromUrl parseProductFromUrlUseCase;

    private int requestCode;

    @Inject
    public TopWebsitePresenter(ParseProductFromUrl parseProductFromUrlUseCase) {
        this.parseProductFromUrlUseCase = parseProductFromUrlUseCase;
    }

    public void setView(@NonNull TopWebsiteView view) {
        this.topWebsiteView = view;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.parseProductFromUrlUseCase.dispose();
        this.topWebsiteView = null;
    }

    public void navigateToOrderList() {
        if (this.topWebsiteView.activity() instanceof TopWebsiteActivity) {
            ((TopWebsiteActivity) this.topWebsiteView.activity()).navigateToOrderList();
        }
    }

    public void loadContent(String url, String query_search) {
        if (url == null || url.isEmpty()) {
            String default_url = this.topWebsiteView.context().getString(R.string.top_website_url_default, "");
            if (query_search != null && !query_search.isEmpty()) {
                if (URLUtil.isValidUrl(query_search)) {
                    default_url = query_search;
                } else {
                    default_url = this.topWebsiteView.context().getString(R.string.top_website_url_default, query_search);
                }
            }
            this.topWebsiteView.loadUrlToWebView(default_url);
        } else {
            if (URLUtil.isValidUrl(url)) {

            } else {
                url = this.topWebsiteView.context().getString(R.string.top_website_url_default, url);
            }
            this.topWebsiteView.loadUrlToWebView(url);
        }
    }

    public void parseUrlToProduct(String url) {
        this.hideViewRetry();
        this.showViewLoading();
        this.parse(url);
    }

    private void showViewLoading() {
        this.topWebsiteView.showLoading();
    }

    private void hideViewLoading() {
        this.topWebsiteView.hideLoading();
    }

    private void showViewRetry() {
        this.topWebsiteView.showRetry();
    }

    private void hideViewRetry() {
        this.topWebsiteView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.topWebsiteView.context(),
                errorBundle.getException());
        this.topWebsiteView.showError(errorMessage);
    }

    private void parse(String url) {
        String token = PreferencesUtility.getInstance(topWebsiteView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.parseProductFromUrlUseCase.execute(new ParseProductFromUrlObserver(), ParseProductFromUrl.Params.forParseUrl(token, url));
    }

    private void navigationProduct(Product product) {
        if (topWebsiteView.activity() instanceof TopWebsiteActivity) {
            ((TopWebsiteActivity) topWebsiteView.activity()).navigateToProduct(product, requestCode);
        }
    }

    private final class ParseProductFromUrlObserver extends DefaultObserver<Product> {

        @Override
        public void onComplete() {
            TopWebsitePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            TopWebsitePresenter.this.hideViewLoading();
            TopWebsitePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            TopWebsitePresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Product product) {
            if (product != null) {
                if (Constants.CURRENCY_SHIP != null) {
                    if (!product.getCurrency().equals(Constants.CURRENCY_SHIP)) {
                        topWebsiteView.showError(topWebsiteView.context().getString(R.string.top_website_invalid_current));
                        return;
                    }
                } else {
                    Constants.CURRENCY_SHIP = product.getCurrency();
                }
                if (Constants.COUNTRY != null) {
                    if (!product.getCountry().getIso().equals(Constants.COUNTRY.getIso())) {
                        topWebsiteView.showError(topWebsiteView.context().getString(R.string.top_website_invalid_ship_country));
                        return;
                    }
                } else {
                    Constants.COUNTRY = product.getCountry();
                    Constants.COUNTRY.setIos(product.getCountry().getIso());
                }
                Product p = Constants.PRODUCTS.get(product.getLink());
                int quantity = 0;
                if (p != null) {
                    quantity = Integer.valueOf(p.getQuantity());
                }
                quantity += 1;
                product.setQuantity(quantity + "");//set default quantity
                TopWebsitePresenter.this.navigationProduct(product);
            }
        }
    }

}
