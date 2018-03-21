package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.EventModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.model.TestOrderModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;

/**
 * Created by vandongluong on 3/7/18.
 */

public interface TestOrderListView extends LoadDataView {
    /**
     * Render a order list in the UI.
     *
     * @param orderModelCollection The collection of {@link TestOrderModel} that will be shown.
     */
    void renderOrderList(Collection<TestOrderModel> orderModelCollection, boolean isAppend);

    void showAddUrlDialog();

    void onDismissClicked();

    void renderEventList(Collection<EventModel> eventCollection);

    void showRefreshing();

    void hideRefreshing();

    void loadMoreError();

    void showLoadingMore();

    void hideLoadingMore();
}

