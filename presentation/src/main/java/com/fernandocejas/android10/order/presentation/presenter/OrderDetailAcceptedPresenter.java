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
import com.fernandocejas.android10.order.presentation.mapper.AddressModelDataMapper;
import com.fernandocejas.android10.order.presentation.mapper.OrderModelDataMapper;
import com.fernandocejas.android10.order.presentation.mapper.ProductModelDataMapper;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.OrderDetailAcceptedView;
import com.fernandocejas.android10.order.presentation.view.activity.OrderDetailActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.util.Collection;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class OrderDetailAcceptedPresenter implements Presenter {

    private OrderDetailAcceptedView orderDetailAcceptedView;

    private final GetOrderDetail getOrderDetailUseCase;
    private final OrderModelDataMapper orderModelDataMapper;
    private final ProductModelDataMapper productModelDataMapper;
    private final AddressModelDataMapper addressModelDataMapper;

    private Order order;
    private Offer provider;
    private float price;
    private float sale_tax;
    private float service_fee;
    private float provider_fee;
    private float ship_fee;
    private float surcharge_fee;
    private float other_fee;

    @Inject
    public OrderDetailAcceptedPresenter(GetOrderDetail getOrderDetail,
                                        OrderModelDataMapper orderModelDataMapper,
                                        ProductModelDataMapper productModelDataMapper,
                                        AddressModelDataMapper addressModelDataMapper) {
        this.getOrderDetailUseCase = getOrderDetail;
        this.orderModelDataMapper = orderModelDataMapper;
        this.productModelDataMapper = productModelDataMapper;
        this.addressModelDataMapper = addressModelDataMapper;
    }

    public void setView(@NonNull OrderDetailAcceptedView view) {
        this.orderDetailAcceptedView = view;
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
        this.orderDetailAcceptedView = null;
    }

    public float getProvider_fee() {
        return provider_fee;
    }

    public float getShip_fee() {
        return ship_fee;
    }

    public float getSurcharge_fee() {
        return surcharge_fee;
    }

    public float getOther_fee() {
        return other_fee;
    }

    public float getTotal() {
        return price + sale_tax + service_fee + provider_fee + ship_fee + surcharge_fee + other_fee;
    }

    private void showViewLoading() {
        this.orderDetailAcceptedView.showLoading();
    }

    private void hideViewLoading() {
        this.orderDetailAcceptedView.hideLoading();
    }

    private void showViewRetry() {
        this.orderDetailAcceptedView.showRetry();
    }

    private void hideViewRetry() {
        this.orderDetailAcceptedView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.orderDetailAcceptedView.context(),
                errorBundle.getException());
        this.orderDetailAcceptedView.showError(errorMessage);
    }

    public void setOrder(Order order) {
        this.order = order;
        if (order != null) {
            try {
                this.price = Float.valueOf(order.getAmount());
            } catch (Exception ex) {
            }
            try {
                this.sale_tax = Float.valueOf(order.getTax());
            } catch (Exception ex) {
            }
            try {
                this.service_fee = Float.valueOf(order.getServicefee());
            } catch (Exception ex) {
            }
            Offer provider = getProvider(this.order.getOffers(), this.order.getProviderid());
            setProvider(provider);
            if (provider != null) {
                this.orderDetailAcceptedView.showProviderInView(
                        provider.getLogo(),
                        provider.getName(),
                        this.addressModelDataMapper.transform(provider.getAddress()));
            }
            this.orderDetailAcceptedView.showOrderDetailInView(orderModelDataMapper.transform(order));
        }
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

    public void gotoChatScreen() {
        navigateToChat();
    }

    private void getOrderDetail(String order_id) {
        String token = PreferencesUtility.getInstance(orderDetailAcceptedView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.getOrderDetailUseCase.execute(new OrderDetailObserver(), GetOrderDetail.Params.forOrderDetail(token, order_id));
    }

    public void setProvider(Offer provider) {
        this.provider = provider;
        try {
            this.provider_fee = Float.valueOf(provider.getProviderfee());
        } catch (Exception ex) {
        }
        try {
            this.ship_fee = Float.valueOf(provider.getShipfee());
        } catch (Exception ex) {
        }
        try {
            this.surcharge_fee = Float.valueOf(provider.getSurchargefee());
        } catch (Exception ex) {
        }
        try {
            this.other_fee = Float.valueOf(provider.getOtherfee());
        } catch (Exception ex) {
        }
    }

    public void navigateToOrderList() {
        if (this.orderDetailAcceptedView.activity() instanceof OrderDetailActivity) {
            ((OrderDetailActivity) orderDetailAcceptedView.activity()).navigateToOrderList();
        }
    }

    private void navigateToProductDetail(Product product) {
        if (this.orderDetailAcceptedView.activity() instanceof OrderDetailActivity) {
            ((OrderDetailActivity) orderDetailAcceptedView.activity()).navigateToProductDetail(product);
        }
    }

    private void navigateToChat() {
        if (this.order == null) {
            return;
        }
        if (this.orderDetailAcceptedView.activity() instanceof OrderDetailActivity) {
            String provider_avatar = null;
            if (this.provider != null) {
                provider_avatar = this.provider.getLogo();
            }
            ((OrderDetailActivity) orderDetailAcceptedView.activity()).navigateToChat(order.getId(), order.getProviderid(), Constants.USER_ID, provider_avatar);
        }
    }

    private Offer getProvider(Collection<Offer> offers, String provider_id) {
        for (Offer offer : offers) {
            if (provider_id.equals(offer.getProviderid())) {
                return offer;
            }
        }
        return null;
    }

    private final class OrderDetailObserver extends DefaultObserver<Order> {

        @Override
        public void onComplete() {
            OrderDetailAcceptedPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            OrderDetailAcceptedPresenter.this.hideViewLoading();
            OrderDetailAcceptedPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            OrderDetailAcceptedPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Order order) {
            OrderDetailAcceptedPresenter.this.setOrder(order);
        }
    }

}
