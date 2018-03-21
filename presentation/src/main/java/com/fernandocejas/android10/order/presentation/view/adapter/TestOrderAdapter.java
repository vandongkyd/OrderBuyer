package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.model.TestOrderModel;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.OrderAdapterView;

import junit.framework.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vandongluong on 3/7/18.
 */

public class TestOrderAdapter extends RecyclerView.Adapter<TestOrderAdapter.OrderViewHolder> implements OrderAdapterView {
    public interface OnItemClickListener {
        void onOrderItemClicked(TestOrderModel orderModel);

        void showMoreImageClicked(Collection<ImageModel> imageModels);
    }

    private List<TestOrderModel> ordersCollection;

    private OrderAdapter.OnItemClickListener onItemClickListener;

    private Context context;
    private LayoutInflater layoutInflater;

    @Inject
    TestOrderAdapter(Context context) {
        this.context = context;
        this.ordersCollection = Collections.emptyList();
    }
    @Override
    public TestOrderAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.layoutInflater == null) {
            this.layoutInflater = LayoutInflater.from(parent.getContext());
        }
        final View view = this.layoutInflater.inflate(R.layout.row_order, parent, false);
        return new TestOrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestOrderAdapter.OrderViewHolder holder, int position) {
        final TestOrderModel orderModel = this.ordersCollection.get(position);
        //show offer list

    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setOrdersCollection(Collection<TestOrderModel> ordersCollection, boolean isAppend) {
        this.validateOrdersCollection(ordersCollection);
        if (!isAppend) {
            this.ordersCollection = (List<TestOrderModel>) ordersCollection;
            this.notifyDataSetChanged();
        } else {
            int item_count = getItemCount();
            this.ordersCollection.addAll(ordersCollection);
            this.notifyItemRangeInserted(item_count, getItemCount() - item_count);
        }
    }

    private void validateOrdersCollection(Collection<TestOrderModel> ordersCollection) {
        if (ordersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }
    @Override
    public int getItemCount() {
        return (this.ordersCollection != null) ? this.ordersCollection.size() : 0;
    }

    private String getOrderDate(String date) {
        return Utils.getTimeAgos(date);
    }

    private String getDeliveryDate(String date) {
        return Utils.getTimeByFormat(date);
    }

    private String getPriceWithSymbolCurrency(String amount, String currency) {
        String full_price = "";
        String symbol = Utils.getCurrencySymbol(currency);
        full_price = symbol + "" + Utils.formatDecimal(amount);
        return full_price;
    }
    @Override
    public void renderOfferList(OrderAdapter.OrderViewHolder viewHolder, Collection<OfferModel> offerModels, String status, String provider_id) {

    }

    @Override
    public void renderImageList(OrderAdapter.OrderViewHolder viewHolder, Collection<ImageModel> imageModels) {

    }

    @Override
    public void showDescriptionInView(OrderAdapter.OrderViewHolder viewHolder, String description) {

    }

    @Override
    public void showShippingInfoInView(OrderAdapter.OrderViewHolder viewHolder, String deliveryFrom, String deliveryTo) {

    }

    @Override
    public void showPriceInView(OrderAdapter.OrderViewHolder viewHolder, String price) {

    }

    @Override
    public void showOrderDateInView(OrderAdapter.OrderViewHolder viewHolder, String date_ago) {

    }

    @Override
    public void showShipFeeInView(OrderAdapter.OrderViewHolder viewHolder, String price, String status) {

    }

    @Override
    public void showDeliveryDateInView(OrderAdapter.OrderViewHolder viewHolder, String date, String status) {

    }

    @Override
    public void showStatusInView(OrderAdapter.OrderViewHolder viewHolder, String status) {
        viewHolder.imv_status.setVisibility(View.VISIBLE);
        switch (status) {
            case "0":
                viewHolder.imv_status.setImageResource(R.drawable.sts_1);
                break;
            case "1":
                viewHolder.imv_status.setImageResource(R.drawable.sts_2);
                break;
            case "2":
                viewHolder.imv_status.setImageResource(R.drawable.sts_3);
                break;
            case "3":
                viewHolder.imv_status.setImageResource(R.drawable.sts_4);
                break;
            case "4":
                viewHolder.imv_status.setImageResource(R.drawable.sts_5);
                break;
            case "5":
                viewHolder.imv_status.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
    @Override
    public void onOrderItemClicked(OrderModel orderModel) {

    }
    public class OrderViewHolder extends RecyclerView.ViewHolder {
        @Bind({R.id.imv_1, R.id.imv_2, R.id.imv_3})
        ImageView[] imv_product_images;
        @Bind(R.id.tv_description)
        TextView tv_description;
        @Bind(R.id.tv_from)
        TextView tv_from;
        @Bind(R.id.tv_to)
        TextView tv_to;
        @Bind(R.id.tv_no_offer)
        TextView tv_no_offer;
        @Bind(R.id.tv_cash)
        TextView tv_cash;
        @Bind(R.id.tv_order_time)
        TextView tv_order_time;
        @Bind(R.id.tv_ship_fee)
        TextView tv_ship_fee;
        @Bind(R.id.tv_time_receive)
        TextView tv_time_receive;
        @Bind({R.id.imv_offer_1, R.id.imv_offer_2})
        ImageView[] imv_offer;
        @Bind(R.id.tv_offer_more)
        TextView tv_offer_more;
        @Bind(R.id.rlt_offers)
        RelativeLayout rlt_offers;
        @Bind(R.id.tv_cancel)
        TextView tv_offer_cancel;
        @Bind(R.id.tv_image_more)
        TextView tv_image_more;
        @Bind({R.id.layout_imv_1, R.id.layout_imv_2, R.id.layout_imv_3})
        FrameLayout[] layout_imv;
        @Bind(R.id.imv_status)
        ImageView imv_status;
        @Bind(R.id.v_description_padding)
        View v_description_padding;
        @Bind(R.id.ln_ship_fee)
        LinearLayout ln_ship_fee;
        @Bind(R.id.ln_time_receive)
        LinearLayout ln_time_receive;
        @Bind(R.id.tv_cash_no_offer)
        TextView tv_cash_no_offer;
        @Bind(R.id.tv_order_time_no_offer)
        TextView tv_order_time_no_offer;
        @Bind(R.id.lyt_with_none_offer)
        FrameLayout lyt_with_none_offer;
        @Bind(R.id.lyt_with_offer)
        LinearLayout lyt_with_offer;
        @Bind(R.id.cmd_show_image)
        LinearLayout cmd_show_image;

        OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
