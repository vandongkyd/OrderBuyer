package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.model.TickerModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.Collection;

/**
 * Created by vandongluong on 3/8/18.
 */

public interface TickerListView extends LoadDataView {
    void renderTickerMethodList(Collection<TickerModel> tickerModels);

    void onAddTickerClicked();

    void onBackClicked();

    void onSaveTickerInDialogClicked(String card_number, int exp_month, int exp_year, String cvc);

    void onTickerRemoveClicked(PaymentModel paymentModel, int position);

    void showDeleteSuccess(int position);
}
