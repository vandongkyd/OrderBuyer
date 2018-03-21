/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.CountryModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(CountryModel countryModel);
    }

    private List<CountryModel> countriesCollection;
    private LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    private Context context;

    @Inject
    CountryAdapter(Context context) {
        this.countriesCollection = Collections.emptyList();
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return (this.countriesCollection != null) ? this.countriesCollection.size() : 0;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.layoutInflater == null) {
            this.layoutInflater = LayoutInflater.from(parent.getContext());
        }
        final View view = this.layoutInflater.inflate(R.layout.row_phone_code, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, final int position) {
        final CountryModel countryModel = this.countriesCollection.get(position);
        holder.tv_country_name.setText(countryModel.getName());
        holder.tv_country_phone_code.setText("+" + countryModel.getDial_code());
        Glide.with(context)
                .load(Uri.parse("file:///android_asset/flag/" + countryModel.getCode().toLowerCase() + ".png"))
                .into(holder.imv_country_flag);
//        holder.imv_country_flag.setImageResource(countryModel.getFlag());
        holder.itemView.setOnClickListener(v -> {
            if (CountryAdapter.this.onItemClickListener != null) {
                CountryAdapter.this.onItemClickListener.onItemClicked(countryModel);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setCountriesCollection(Collection<CountryModel> countriesCollection) {
        this.validateCountriesCollection(countriesCollection);
        this.countriesCollection = (List<CountryModel>) countriesCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateCountriesCollection(Collection<CountryModel> countriesCollection) {
        if (countriesCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imv_country_flag)
        ImageView imv_country_flag;
        @Bind(R.id.tv_country_name)
        TextView tv_country_name;
        @Bind(R.id.tv_country_phone_code)
        TextView tv_country_phone_code;

        CountryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
