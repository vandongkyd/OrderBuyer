package com.fernandocejas.android10.order.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.domain.Event;
import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.TestOrder;
import com.fernandocejas.android10.order.domain.interactor.GetEventList;
import com.fernandocejas.android10.order.domain.interactor.GetOrderList;
import com.fernandocejas.android10.order.presentation.mapper.OrderModelDataMapper;
import com.fernandocejas.android10.order.presentation.mapper.TestOrderModelMapper;
import com.fernandocejas.android10.order.presentation.model.EventModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.model.TestOrderModel;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.OrderListView;
import com.fernandocejas.android10.order.presentation.view.TestOrderListView;
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

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class TestOrderListPresenter implements Presenter {
    private TestOrderListView viewListView1;
    private final TestOrderModelMapper orderModelDataMapper;

    public TestOrderListPresenter(TestOrderModelMapper orderModelDataMapper) {
        this.orderModelDataMapper = orderModelDataMapper;
    }
    public void setView(@NonNull TestOrderListView view) {
        this.viewListView1 = view;
    }
    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
//        this.getOrderListUseCase.dispose();
//        this.getEventListUseCase.dispose();
        this.viewListView1= null;
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
       // this.getOrderList();
    }

    public void refreshOrderList() {
        this.showViewRefreshing();
        //this.getOrderListByRefresh();
    }

    public void loadMoreOrderList(String offset) {
        this.showViewLoadingMore();
        //this.getOrderListWithOffset(offset);
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
        Utils.openDefaultBrowser(this.viewListView1.activity(), link);
    }

    public void addUrl(String url) {
        if (this.viewListView1.activity() instanceof OrderListActivity) {
            navigateToTopWebsite(url);
            this.emptyOrder();
        }
    }

    private void navigateToTopWebsite(String url){
        ((OrderListActivity) this.viewListView1.activity()).navigateToTopWebsite(url);
    }

    private void navigateToOrderDetail(String id, String status) {
        ((OrderListActivity) this.viewListView1.activity()).navigateToOrderDetail(id, status);
    }

    private void showViewLoading() {
        this.viewListView1.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView1.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView1.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView1.hideRetry();
    }

    private void showViewRefreshing() {
        this.viewListView1.showRefreshing();
    }

    private void hideViewRefreshing() {
        this.viewListView1.hideRefreshing();
    }

    private void showViewLoadingMore() {
        this.viewListView1.showLoadingMore();
    }

    private void hideViewLoadingMore() {
        this.viewListView1.hideLoadingMore();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView1.context(),
                errorBundle.getException());
        this.viewListView1.showError(errorMessage);
    }
    private void getEventList() {
        String token = PreferencesUtility.getInstance(viewListView1.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        //this.getEventListUseCase.execute(new EventListObserver(), GetEventList.Params.forEvent(token));

    }

    private void showOrdersCollectionInView(Collection<TestOrder> ordersCollection, boolean isAppend) {
        final Collection<TestOrderModel> orderModelsCollection =
                this.orderModelDataMapper.transformto(ordersCollection);
        this.viewListView1.renderOrderList(orderModelsCollection, isAppend);
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
        this.viewListView1.loadMoreError();
    }

    private final class TestOrderListObserver extends DefaultObserver<List<TestOrder>> {

        @Override
        public void onComplete() {
            TestOrderListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            TestOrderListPresenter.this.hideViewLoading();
            TestOrderListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            TestOrderListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<TestOrder> orders) {
            TestOrderListPresenter.this.showOrdersCollectionInView(orders, false);
        }
    }

    private final class OrderListWithOffsetObserver extends DefaultObserver<List<TestOrder>> {

        @Override
        public void onComplete() {
            TestOrderListPresenter.this.hideViewLoadingMore();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            TestOrderListPresenter.this.hideViewLoadingMore();
            TestOrderListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            TestOrderListPresenter.this.loadMoreError();
        }

        @Override
        public void onNext(List<TestOrder> orders) {
            TestOrderListPresenter.this.showOrdersCollectionInView(orders, true);
        }
    }

    private final class OrderListByRefreshObserver extends DefaultObserver<List<TestOrder>> {

        @Override
        public void onComplete() {
            TestOrderListPresenter.this.hideViewRefreshing();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            TestOrderListPresenter.this.hideViewRefreshing();
            TestOrderListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(List<TestOrder> orders) {
            TestOrderListPresenter.this.showOrdersCollectionInView(orders, false);
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
    }

}

