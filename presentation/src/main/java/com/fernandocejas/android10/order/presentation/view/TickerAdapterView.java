package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.model.TickerModel;
import com.fernandocejas.android10.order.presentation.view.adapter.PaymentAdapter;
import com.fernandocejas.android10.order.presentation.view.adapter.ProductAdapter;
import com.fernandocejas.android10.order.presentation.view.adapter.TickerAdapter;

/**
 * Created by vandongluong on 3/8/18.
 */

public interface TickerAdapterView {
    void showNameTickerInView(TickerAdapter.ItemBaseViewHolder viewHolder, String name);

    void showSymbolInView(TickerAdapter.ItemBaseViewHolder viewHolder, String symbol);

    void showPrice_usdInView(TickerAdapter.ItemBaseViewHolder viewHolder, String price_usd);

    void onItemClicked(TickerModel tickerModel);

    void onItemRemoved(TickerModel tickerModel, int position);

    void onLinkClicked(TickerModel tickerModel);

}
