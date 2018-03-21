package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.view.PaymentSpinnerAdapterView;
import com.stripe.android.model.Card;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.stripe.android.model.Card.BRAND_RESOURCE_MAP;

/**
 *
 *
 */

public class PaymentSpinnerAdapter extends BaseAdapter implements PaymentSpinnerAdapterView {

    private ArrayList<PaymentModel> mList;
    private OnItemClicked onItemClicked;
    private Context context;

    public interface OnItemClicked {
        void onItemClicked(PaymentModel paymentModel);
    }

    @Inject
    public PaymentSpinnerAdapter(Context context) {
        this.context = context;
        this.mList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PaymentModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, parent);
    }

    private View createItemView(int position, ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_payment, parent, false);

        final PaymentModel model = mList.get(position);

        view.setTag(new ItemBaseViewHolder(view));
        onBindViewHolder((ItemBaseViewHolder) view.getTag(), parent, model);
        return view;
    }

    public void onBindViewHolder(final ItemBaseViewHolder holder, ViewGroup parent, final PaymentModel paymentModel) {

        //show last 4
        showLast4InView(holder, paymentModel.getLast4());

        //show icon brand
        showIconInView(holder, paymentModel.getBrand());

        //action events
        holder.lyt_main.setOnClickListener(v -> {
            this.onItemClicked.onItemClicked(paymentModel);
            // close the dropdown
            {
                View root = parent.getRootView();
                root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            }
        });
    }

    public void replaceWith(Collection<PaymentModel> newValues) {
        this.mList.clear();
        this.mList.addAll(newValues);
        notifyDataSetChanged();
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    @Override
    public void showLast4InView(ItemBaseViewHolder viewHolder, String last4) {
        viewHolder.tv_text.setText(last4);
    }

    @Override
    public void showIconInView(ItemBaseViewHolder viewHolder, String brand) {
        if (Card.UNKNOWN.equals(brand)) {
            Drawable icon = context.getResources().getDrawable(R.drawable.ic_credit_card);
            viewHolder.imv_brand.setImageDrawable(icon);
        } else {
            Integer resID = BRAND_RESOURCE_MAP.get(brand);
            if (resID != null) {
                viewHolder.imv_brand.setImageResource(resID);
            }
        }
    }

    public class ItemBaseViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.lyt_main)
        LinearLayout lyt_main;
        @Bind(R.id.tv_text)
        TextView tv_text;
        @Bind(R.id.imv_brand)
        ImageView imv_brand;

        public ItemBaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
