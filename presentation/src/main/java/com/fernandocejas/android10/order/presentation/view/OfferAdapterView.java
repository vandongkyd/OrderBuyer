/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.domain.Rate;
import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.order.presentation.model.RateModel;
import com.fernandocejas.android10.order.presentation.view.adapter.OfferAdapter;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface OfferAdapterView {

    void showAvatarInView(OfferAdapter.OfferViewHolder viewHolder, String url_avatar);

    void showNameInView(OfferAdapter.OfferViewHolder viewHolder, String name);

    void showRatingInView(OfferAdapter.OfferViewHolder viewHolder, RateModel rate);

    void showDescriptionInView(OfferAdapter.OfferViewHolder viewHolder, String description);

    void showOrderDateInView(OfferAdapter.OfferViewHolder viewHolder, String order_date);

    void showPriceInView(OfferAdapter.OfferViewHolder viewHolder, String price);

    void showCurrencySymbolInView(OfferAdapter.OfferViewHolder viewHolder, String symbol);

    void onSendMessageClicked(OfferModel offerModel);

    void onConfirmClicked(OfferModel offerModel);

}

