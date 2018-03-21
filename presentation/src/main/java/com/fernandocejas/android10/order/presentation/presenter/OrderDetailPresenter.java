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

import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.domain.interactor.GetOrderDetail;
import com.fernandocejas.android10.order.presentation.mapper.OfferModelDataMapper;
import com.fernandocejas.android10.order.presentation.mapper.OrderModelDataMapper;
import com.fernandocejas.android10.order.presentation.mapper.ProductModelDataMapper;
import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.OrderDetailView;
import com.fernandocejas.android10.order.presentation.view.activity.OrderDetailActivity;
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
public class OrderDetailPresenter implements Presenter {

    private OrderDetailView orderDetailView;

    private final GetOrderDetail getOrderDetailUseCase;
    private final OrderModelDataMapper orderModelDataMapper;
    private final ProductModelDataMapper productModelDataMapper;
    private final OfferModelDataMapper offerModelDataMapper;

    private Order order;
    private OfferModel provider;

    @Inject
    public OrderDetailPresenter(GetOrderDetail getOrderDetail,
                                OrderModelDataMapper orderModelDataMapper,
                                ProductModelDataMapper productModelDataMapper,
                                OfferModelDataMapper offerModelDataMapper) {
        this.getOrderDetailUseCase = getOrderDetail;
        this.orderModelDataMapper = orderModelDataMapper;
        this.productModelDataMapper = productModelDataMapper;
        this.offerModelDataMapper = offerModelDataMapper;
    }

    public void setView(@NonNull OrderDetailView view) {
        this.orderDetailView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getOrderDetailUseCase.dispose();
        this.orderDetailView = null;
    }

    private void showViewLoading() {
        this.orderDetailView.showLoading();
    }

    private void hideViewLoading() {
        this.orderDetailView.hideLoading();
    }

    private void showViewRetry() {
        this.orderDetailView.showRetry();
    }

    private void hideViewRetry() {
        this.orderDetailView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.orderDetailView.context(),
                errorBundle.getException());
        this.orderDetailView.showError(errorMessage);
    }

    public void setProvider(OfferModel provider) {
        this.provider = provider;
    }

    public void loadOrderDetail(String order_id) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getOrderDetail(order_id);
    }

    public void showProductDetail(ProductModel productModel) {
        if (productModel != null) {
            navigateToProductDetail(productModelDataMapper.transform(productModel));
        }
    }

    private void getOrderDetail(String order_id) {
        String token = PreferencesUtility.getInstance(orderDetailView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.getOrderDetailUseCase.execute(new OrderDetailObserver(), GetOrderDetail.Params.forOrderDetail(token, order_id));
    }

    private void showOrderInView(Order order) {
        if (order != null) {
            this.orderDetailView.showOrderDetailInView(orderModelDataMapper.transform(order));
        }
    }

    public void navigateToOrderList() {
        if (this.orderDetailView.activity() instanceof OrderDetailActivity) {
            ((OrderDetailActivity) orderDetailView.activity()).navigateToOrderList();
        }
    }

    private void navigateToProductDetail(Product product) {
        if (this.orderDetailView.activity() instanceof OrderDetailActivity) {
            ((OrderDetailActivity) orderDetailView.activity()).navigateToProductDetail(product);
        }
    }

    private void navigateToAcceptedScreen(Offer offer,
                                          String quantity,
                                          String amount,
                                          String weight,
                                          String sale_tax,
                                          String service_fee,
                                          String currency) {
        if (this.orderDetailView.activity() instanceof OrderDetailActivity) {
            ((OrderDetailActivity) orderDetailView.activity()).navigateToAcceptedOffer(offer, quantity, amount, weight, sale_tax, service_fee, currency);
        }
    }

    private void navigateToChatScreen() {
        if (this.order == null) {
            return;
        }
        if (this.provider == null) {
            return;
        }
        if (this.orderDetailView.activity() instanceof OrderDetailActivity) {
            ((OrderDetailActivity) orderDetailView.activity()).navigateToChat(order.getId(), this.provider.getProviderid(), Constants.USER_ID, this.provider.getLogo());
        }
    }

    public void gotoAcceptedScreen(OfferModel offer) {
        if (offer != null) {
            navigateToAcceptedScreen(offerModelDataMapper.transform(offer),
                    order.getQuantity(),
                    order.getAmount(),
                    order.getWeight(),
                    order.getTax(),
                    order.getServicefee(),
                    order.getCurrency());
        }
    }

    public void gotoChatScreen() {
        this.navigateToChatScreen();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private final class OrderDetailObserver extends DefaultObserver<Order> {

        @Override
        public void onComplete() {
            OrderDetailPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            OrderDetailPresenter.this.hideViewLoading();
            OrderDetailPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            OrderDetailPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Order order) {
            OrderDetailPresenter.this.showOrderInView(order);
            OrderDetailPresenter.this.setOrder(order);
        }
    }

}
