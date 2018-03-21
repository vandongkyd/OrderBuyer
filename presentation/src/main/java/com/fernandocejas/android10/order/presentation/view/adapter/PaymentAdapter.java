package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.view.PaymentAdapterView;
import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;
import com.stripe.android.model.Card;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.stripe.android.model.Card.BRAND_RESOURCE_MAP;

/**
 *
 *
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ItemBaseViewHolder> implements PaymentAdapterView {

    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;
    public static final int ITEM_TYPE_NO_SWIPE = 1003;

    public interface OnItemClickListener {

        void onItemClicked(PaymentModel paymentModel);

        void onItemRemoved(PaymentModel paymentModel, int position);

    }

    private List<PaymentModel> paymentModels;
    private final Context context;

    private OnItemClickListener onItemClickListener;
    private ItemTouchHelperExtension mItemTouchHelperExtension;

    @Inject
    PaymentAdapter(Context context) {
        this.context = context;
        this.paymentModels = Collections.emptyList();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH_NO_SPRING;
    }

    @Override
    public int getItemCount() {
        return (this.paymentModels != null) ? this.paymentModels.size() : 0;
    }

    public void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }

    @Override
    public ItemBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater(parent.getContext()).inflate(R.layout.row_payment, parent, false);
        if (viewType == ITEM_TYPE_ACTION_WIDTH) return new ItemSwipeWithActionWidthViewHolder(view);
        if (viewType == ITEM_TYPE_NO_SWIPE) return new ItemNoSwipeViewHolder(view);
        if (viewType == ITEM_TYPE_RECYCLER_WIDTH) {
            view = getLayoutInflater(parent.getContext()).inflate(R.layout.row_payment_with_single_delete, parent, false);
            return new ItemViewHolderWithRecyclerWidth(view);
        }
        return new ItemSwipeWithActionWidthNoSpringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemBaseViewHolder holder, final int position) {
        final PaymentModel paymentModel = this.paymentModels.get(position);
        //show last 4
        showLast4InView(holder, paymentModel.getLast4());

        //show icon brand
        showIconInView(holder, paymentModel.getBrand());

        //action events
        holder.itemView.setOnClickListener(v -> {
            if (PaymentAdapter.this.onItemClickListener != null) {
                PaymentAdapter.this.onItemClickListener.onItemClicked(paymentModel);
            }
        });
        if (holder instanceof ItemViewHolderWithRecyclerWidth) {
            ItemViewHolderWithRecyclerWidth viewHolder = (ItemViewHolderWithRecyclerWidth) holder;
            viewHolder.view_list_repo_action_delete.setOnClickListener(
                    view -> this.onItemClickListener.onItemRemoved(paymentModel, position)
            );
        } else if (holder instanceof ItemSwipeWithActionWidthViewHolder) {
            ItemSwipeWithActionWidthViewHolder viewHolder = (ItemSwipeWithActionWidthViewHolder) holder;
            viewHolder.view_list_repo_action_update.setOnClickListener(
                    view -> {
                        //on update clicked
                        //...
                        //
                        mItemTouchHelperExtension.closeOpened();
                    }

            );
            viewHolder.view_list_repo_action_delete.setOnClickListener(
                    view -> this.onItemClickListener.onItemRemoved(paymentModel, position)
            );
        }
    }

    public void move(int from, int to) {
        PaymentModel prev = paymentModels.remove(from);
        paymentModels.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPaymentModels(Collection<PaymentModel> paymentModels) {
        this.validateCollection(paymentModels);
        this.paymentModels = (List<PaymentModel>) paymentModels;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(PaymentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateCollection(Collection<PaymentModel> eventsCollection) {
        if (eventsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
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

    public void onItemRemoved(int position) {
        this.paymentModels.remove(position);
        notifyItemRemoved(position);
    }

    public class ItemBaseViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_text)
        TextView tv_text;
        @Bind(R.id.imv_brand)
        ImageView imv_brand;
        @Bind(R.id.view_list_main_content)
        View view_list_main_content;
        @Bind(R.id.view_list_repo_action_container)
        View view_list_repo_action_container;

        public ItemBaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolderWithRecyclerWidth extends ItemBaseViewHolder {

        @Bind(R.id.view_list_repo_action_delete)
        View view_list_repo_action_delete;

        public ItemViewHolderWithRecyclerWidth(View itemView) {
            super(itemView);
        }
    }

    class ItemSwipeWithActionWidthViewHolder extends ItemBaseViewHolder implements Extension {

        @Bind(R.id.view_list_repo_action_delete)
        View view_list_repo_action_delete;
        @Bind(R.id.view_list_repo_action_update)
        View view_list_repo_action_update;

        public ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public float getActionWidth() {
            return view_list_repo_action_container.getWidth();
        }
    }

    class ItemSwipeWithActionWidthNoSpringViewHolder extends ItemSwipeWithActionWidthViewHolder implements Extension {

        public ItemSwipeWithActionWidthNoSpringViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public float getActionWidth() {
            return view_list_repo_action_container.getWidth();
        }
    }

    class ItemNoSwipeViewHolder extends ItemBaseViewHolder {

        public ItemNoSwipeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
