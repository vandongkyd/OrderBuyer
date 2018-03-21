package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.view.AddressAdapterView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 *
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> implements AddressAdapterView {


    public interface OnItemClickListener {
        void onItemClicked(AddressModel addressModel);
    }

    private List<AddressModel> addressModels;
    private final LayoutInflater layoutInflater;

    private final Context context;

    private AddressAdapter.OnItemClickListener onItemClickListener;

    @Inject
    AddressAdapter(Context context) {
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addressModels = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.addressModels != null) ? this.addressModels.size() : 0;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, final int position) {
        final AddressModel addressModel = this.addressModels.get(position);
        //show address
        showAddressInView(holder, addressModel);

        //action events
        holder.itemView.setOnClickListener(v -> {
            if (AddressAdapter.this.onItemClickListener != null) {
                AddressAdapter.this.onItemClickListener.onItemClicked(addressModel);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setAddressModels(Collection<AddressModel> addressModels) {
        this.validateCollection(addressModels);
        this.addressModels = (List<AddressModel>) addressModels;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(AddressAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateCollection(Collection<AddressModel> addressModels) {
        if (addressModels == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    private String getAddress(AddressModel addressModel) {
        String a = "";
        if (addressModel == null) {
            return a;
        }
        String address = addressModel.getAddress();
        String city = addressModel.getCity();
        String country = addressModel.getCountry();
        if (address != null) {
            a += address;
        }
        if (city != null) {
            if (!a.isEmpty()) {
                a += " ";
            }
            a += city;
        }
        if (country != null) {
            if (!a.isEmpty()) {
                a += " ";
            }
            a += country;
        }
        return a;
    }

    @Override
    public void showAddressInView(AddressAdapter.AddressViewHolder viewHolder, AddressModel addressModel) {
        String address_full = getAddress(addressModel);
        viewHolder.tv_text.setText(address_full);
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_text)
        TextView tv_text;

        AddressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
