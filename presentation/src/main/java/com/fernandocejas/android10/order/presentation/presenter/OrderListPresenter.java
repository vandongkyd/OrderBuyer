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

import com.fernandocejas.android10.order.domain.Event;
import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.interactor.GetEventList;
import com.fernandocejas.android10.order.domain.interactor.GetOrderList;
import com.fernandocejas.android10.order.presentation.mapper.EventModelDataMapper;
import com.fernandocejas.android10.order.presentation.mapper.OrderModelDataMapper;
import com.fernandocejas.android10.order.presentation.model.EventModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.OrderListView;
import com.fernandocejas.android10.order.presentation.view.activity.OrderListActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class OrderListPresenter implements Presenter {

    private OrderListView viewListView;

    private final GetOrderList getOrderListUseCase;
    private final GetEventList getEventListUseCase;

    private final OrderModelDataMapper orderModelDataMapper;
    private final EventModelDataMapper eventModelDataMapper;


    @Inject
    public OrderListPresenter(GetOrderList getOrderListUseCase,
                              GetEventList getEventListUseCase,
                              OrderModelDataMapper orderModelDataMapper,
                              EventModelDataMapper eventModelDataMapper) {
        this.getOrderListUseCase = getOrderListUseCase;
        this.getEventListUseCase = getEventListUseCase;
        this.orderModelDataMapper = orderModelDataMapper;
        this.eventModelDataMapper = eventModelDataMapper;
    }

    public void setView(@NonNull OrderListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getOrderListUseCase.dispose();
        this.getEventListUseCase.dispose();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the Order list.
     */
    public void initialize() {
        this.loadOrderList();
        this.loadEventList();
    }

    /**
     * Loads orders.
     */
    private void loadOrderList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getOrderList();
    }

    public void refreshOrderList() {
        this.showViewRefreshing();
        this.getOrderListByRefresh();
    }

    public void loadMoreOrderList(String offset) {
        this.showViewLoadingMore();
        this.getOrderListWithOffset(offset);
    }

    private void loadEventList() {
        this.getEventList();
    }

    public void onOrderClicked(OrderModel orderModel) {
        if(orderModel!= null){
            navigateToOrderDetail(orderModel.getId(), orderModel.getStatus());
        }
    }

    public void onEventClicked(EventModel eventModel) {
        String link = eventModel.getLink();
        if (link == null) {
            return;
        }
        Utils.openDefaultBrowser(this.viewListView.activity(), link);
    }

    public void addUrl(String url) {
        if (this.viewListView.activity() instanceof OrderListActivity) {
            navigateToTopWebsite(url);
            this.emptyOrder();
        }
    }

    private void navigateToTopWebsite(String url){
        ((OrderListActivity) this.viewListView.activity()).navigateToTopWebsite(url);
    }

    private void navigateToOrderDetail(String id, String status) {
        ((OrderListActivity) this.viewListView.activity()).navigateToOrderDetail(id, status);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showViewRefreshing() {
        this.viewListView.showRefreshing();
    }

    private void hideViewRefreshing() {
        this.viewListView.hideRefreshing();
    }

    private void showViewLoadingMore() {
        this.viewListView.showLoadingMore();
    }

    private void hideViewLoadingMore() {
        this.viewListView.hideLoadingMore();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showOrdersCollectionInView(Collection<Order> ordersCollection, boolean isAppend) {
        final Collection<OrderModel> orderModelsCollection =
                this.orderModelDataMapper.transform(ordersCollection);
        this.viewListView.renderOrderList(orderModelsCollection, isAppend);
    }

    private void getOrderList() {
        String token = PreferencesUtility.getInstance(viewListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.getOrderListUseCase.execute(new OrderListObserver(), GetOrderList.Params.forOrder(token, 0 + ""));
    }

    private void getOrderListWithOffset(String offset) {
        String token = PreferencesUtility.getInstance(viewListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.getOrderListUseCase.execute(new OrderListWithOffsetObserver(), GetOrderList.Params.forOrder(token, offset));
    }

    private void getOrderListByRefresh() {
        String token = PreferencesUtility.getInstance(viewListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.getOrderListUseCase.execute(new OrderListByRefreshObserver(), GetOrderList.Params.forOrder(token, 0 + ""));
    }

    private void getEventList() {
        String token = PreferencesUtility.getInstance(viewListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.getEventListUseCase.execute(new EventListObserver(), GetEventList.Params.forEvent(token));

    }

    private void showEventCollectionInView(Collection<Event> eventCollection) {
        final Collection<EventModel> eventModelCollection =
                this.eventModelDataMapper.transform(eventCollection);
        this.viewListView.renderEventList(eventModelCollection);
    }

    private void emptyOrder() {
        if (Constants.PRODUCTS == null) {
            Constants.PRODUCTS = new HashMap<>();
        } else {
            Constants.PRODUCTS.clear();
            Constants.COUNTRY = null;
            Constants.CURRENCY_SHIP = null;
        }
    }

    private void loadMoreError() {
        this.viewListView.loadMoreError();
    }

    private final class OrderListObserver extends DefaultObserver<List<Order>> {

        @Override
        public void onComplete() {
            OrderListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            OrderListPresenter.this.hideViewLoading();
            OrderListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            OrderListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Order> orders) {
            OrderListPresenter.this.showOrdersCollectionInView(orders, false);
        }
    }

    private final class OrderListWithOffsetObserver extends DefaultObserver<List<Order>> {

        @Override
        public void onComplete() {
            OrderListPresenter.this.hideViewLoadingMore();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            OrderListPresenter.this.hideViewLoadingMore();
            OrderListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            OrderListPresenter.this.loadMoreError();
        }

        @Override
        public void onNext(List<Order> orders) {
            OrderListPresenter.this.showOrdersCollectionInView(orders, true);
        }
    }

    private final class OrderListByRefreshObserver extends DefaultObserver<List<Order>> {

        @Override
        public void onComplete() {
            OrderListPresenter.this.hideViewRefreshing();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            OrderListPresenter.this.hideViewRefreshing();
            OrderListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(List<Order> orders) {
            OrderListPresenter.this.showOrdersCollectionInView(orders, false);
        }
    }

    private final class EventListObserver extends DefaultObserver<List<Event>> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(List<Event> events) {
            OrderListPresenter.this.showEventCollectionInView(events);
        }
    }

}
