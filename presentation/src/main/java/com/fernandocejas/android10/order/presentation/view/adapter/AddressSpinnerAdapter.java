package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.AddressModel;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

/**
 *
 *
 */

public class AddressSpinnerAdapter extends BaseAdapter {

    private ArrayList<AddressModel> mList;

    private Context mContext;

    @Inject
    public AddressSpinnerAdapter(Context context) {
        this.mList = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public AddressModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final AddressModel model = mList.get(position);

        if (model.isIs_header()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_address_small_header, parent, false);
            view.setTag(new ViewHolder(mContext, view));
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_address_small, parent, false);
            view.setTag(new ViewHolder(mContext, view));
            onBindViewHolder((ViewHolder) view.getTag(), model);
        }

        return view;
    }

    public void onBindViewHolder(final ViewHolder holder, final AddressModel addressModel) {

        //todo: set content
        {
            holder.tv_text.setText(addressModel.getAddress());
        }

    }

    public class ViewHolder {

        private final TextView tv_text;

        public Context mContext;

        public ViewHolder(Context context, View view) {
            this.mContext = context;
            //todo: init views
            {
                tv_text = view.findViewById(R.id.tv_text);
            }
        }
    }

    public void appendWith(Collection<AddressModel> newValues) {
        this.mList.addAll(newValues);
        notifyDataSetChanged();
    }

    public void replaceWith(Collection<AddressModel> newValues) {
        this.mList.clear();
        this.mList.addAll(newValues);
        notifyDataSetChanged();
    }

}
