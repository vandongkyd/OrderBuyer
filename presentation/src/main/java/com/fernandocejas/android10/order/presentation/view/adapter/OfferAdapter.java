/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Rate;
import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.order.presentation.model.RateModel;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.OfferAdapterView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> implements OfferAdapterView {

    public interface OnItemClickListener {
        void onSendMessageClick(OfferModel offerModel);

        void onConfirmClick(OfferModel offerModel);
    }

    private List<OfferModel> offersCollection;

    private OnItemClickListener onItemClickListener;

    private Context context;
    private LayoutInflater layoutInflater;

    private String currency;

    @Inject
    OfferAdapter(Context context) {
        this.offersCollection = Collections.emptyList();
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return (this.offersCollection != null) ? this.offersCollection.size() : 0;
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.layoutInflater == null) {
            this.layoutInflater = LayoutInflater.from(parent.getContext());
        }
        final View view = this.layoutInflater.inflate(R.layout.row_offer, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, final int position) {
        final OfferModel offerModel = this.offersCollection.get(position);
        //show thumbnail
        showAvatarInView(holder, offerModel.getLogo());
        //show name
        showNameInView(holder, offerModel.getName());
        //show rating
        showRatingInView(holder, offerModel.getRate());
        showOrderDateInView(holder, getOrderDate(offerModel.getCreated_at()));
        showDescriptionInView(holder, offerModel.getDescription());
//        String price = getPriceWithSymbolCurrency(getFeeAmount(offerModel) + "", this.currency);
        showPriceInView(holder, getFeeAmount(offerModel) + "");
        showCurrencySymbolInView(holder, Utils.getCurrencySymbol(currency));
        //
        holder.btn_send_message.setOnClickListener(v -> {
            this.onSendMessageClicked(offerModel);
        });
        holder.btn_confirm.setOnClickListener(v -> {
            this.onConfirmClicked(offerModel);
        });
    }

    private String getOrderDate(String order_date) {
        return Utils.getTimeAgos(order_date);
    }

    private float getFeeAmount(OfferModel offerModel) {
        float fee = 0;
        try {
            fee += Float.valueOf(offerModel.getProviderfee());
        } catch (Exception ex) {
        }
        try {
            fee += Float.valueOf(offerModel.getShipfee());
        } catch (Exception ex) {
        }
        try {
            fee += Float.valueOf(offerModel.getSurchargefee());
        } catch (Exception ex) {
        }
        try {
            fee += Float.valueOf(offerModel.getOtherfee());
        } catch (Exception ex) {
        }
        return fee;
    }

    private String getPriceWithSymbolCurrency(String amount, String currency) {
        String full_price = "";
        String symbol = Utils.getCurrencySymbol(currency);
        full_price = symbol + " " + amount;
        return full_price;
    }

    private int getRating(String rating) {
        int r = 0;
        try {
            r = Integer.valueOf(rating);
        } catch (Exception ex) {
        }
        return r;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOffersCollection(Collection<OfferModel> offersCollection) {
        this.validateOffersCollection(offersCollection);
        this.offersCollection = (List<OfferModel>) offersCollection;
        this.notifyDataSetChanged();
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateOffersCollection(Collection<OfferModel> offersCollection) {
        if (offersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    private void loadImageFromUrl(Context context, ImageView view, String url, boolean isCircle, boolean hasDefault) {
        if (url == null || url.isEmpty()) {
            if (hasDefault) {
                //show default avatar if we don't have url to show
                Glide.with(context)
                        .load(R.drawable.default_avatar)
                        .asBitmap()
                        .into(new BitmapImageViewTarget(view) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                view.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
            return;
        }
        if (isCircle) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(new BitmapImageViewTarget(view) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            view.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } else {
            Glide.with(context)
                    .load(url)
                    .into(view);
        }
    }

    @Override
    public void showAvatarInView(OfferAdapter.OfferViewHolder viewHolder, String url_avatar) {
        loadImageFromUrl(context, viewHolder.imv_avatar, url_avatar, false, true);
    }

    @Override
    public void showNameInView(OfferAdapter.OfferViewHolder viewHolder, String name) {
        viewHolder.tv_user_name.setText(name);
    }

    @Override
    public void showRatingInView(OfferAdapter.OfferViewHolder viewHolder, RateModel rate) {
        if (rate != null) {
            viewHolder.rating.setRating(getRating(rate.getStart()));
            viewHolder.tv_rating_count.setText("(" + rate.getCount() + ")");
        }
    }

    @Override
    public void showDescriptionInView(OfferAdapter.OfferViewHolder viewHolder, String description) {
        viewHolder.tv_description.setText(description);
    }

    @Override
    public void showOrderDateInView(OfferAdapter.OfferViewHolder viewHolder, String order_date) {
//        viewHolder.tv_offer_time.setText(order_date);
    }

    @Override
    public void showPriceInView(OfferViewHolder viewHolder, String price) {
//        viewHolder.tv_offer_cash.setText(price);
        viewHolder.tv_price.setText(price);
    }

    @Override
    public void showCurrencySymbolInView(OfferViewHolder viewHolder, String symbol) {
        viewHolder.tv_currency_symbol.setText(symbol);
    }

    @Override
    public void onSendMessageClicked(OfferModel offerModel) {
        if (OfferAdapter.this.onItemClickListener != null) {
            OfferAdapter.this.onItemClickListener.onSendMessageClick(offerModel);
        }
    }

    @Override
    public void onConfirmClicked(OfferModel offerModel) {
        if (OfferAdapter.this.onItemClickListener != null) {
            OfferAdapter.this.onItemClickListener.onConfirmClick(offerModel);
        }
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imv_avatar)
        ImageView imv_avatar;
        @Bind(R.id.tv_user_name)
        TextView tv_user_name;
        @Bind(R.id.tv_description)
        TextView tv_description;
        //        @Bind(R.id.tv_offer_cash)
//        TextView tv_offer_cash;
//        @Bind(R.id.tv_offer_time)
//        TextView tv_offer_time;
        @Bind(R.id.btn_send_message)
        AppCompatButton btn_send_message;
        @Bind(R.id.btn_confirm)
        AppCompatButton btn_confirm;
        @Bind(R.id.tv_rating_count)
        TextView tv_rating_count;
        @Bind(R.id.tv_price)
        TextView tv_price;
        @Bind(R.id.tv_currency_symbol)
        TextView tv_currency_symbol;
        @Bind(R.id.rating)
        AppCompatRatingBar rating;

        OfferViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
