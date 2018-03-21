/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.domain.Event;
import com.fernandocejas.android10.order.presentation.model.EventModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link OrderModel}.
 */
public interface OrderListView extends LoadDataView {
    /**
     * Render a order list in the UI.
     *
     * @param orderModelCollection The collection of {@link OrderModel} that will be shown.
     */
    void renderOrderList(Collection<OrderModel> orderModelCollection, boolean isAppend);

    void showAddUrlDialog();

    void onDismissClicked();

    void renderEventList(Collection<EventModel> eventCollection);

    void showRefreshing();

    void hideRefreshing();

    void loadMoreError();

    void showLoadingMore();

    void hideLoadingMore();
}

