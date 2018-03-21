package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.model.TickerModel;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.view.ProductAdapterView;
import com.fernandocejas.android10.order.presentation.view.TickerAdapterView;
import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vandongluong on 3/8/18.
 */

public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.ItemBaseViewHolder> implements TickerAdapterView {
    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;
    public static final int ITEM_TYPE_NO_SWIPE = 1003;
    public interface OnItemClickListener {

        void onItemClicked(TickerModel tickerModel);

        void onItemRemoved();

        void onLinkClicked(TickerModel tickerModel);
    }
    private List<TickerModel> tickerCollection;
    private String currency_default;
    private Context context;

    private TickerAdapter.OnItemClickListener onItemClickListener;
    private ItemTouchHelperExtension mItemTouchHelperExtension;

    @Inject
    TickerAdapter(Context context) {
        this.context = context;
        this.tickerCollection = Collections.emptyList();
    }
    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH_NO_SPRING;
    }

    @Override
    public int getItemCount() {
        return (this.tickerCollection != null) ? this.tickerCollection.size() : 0;
    }

    public void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }
    @Override
    public TickerAdapter.ItemBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater(parent.getContext()).inflate(R.layout.row_ticker, parent, false);
        if (viewType == ITEM_TYPE_ACTION_WIDTH) return new TickerAdapter.ItemSwipeWithActionWidthViewHolder(view);
        if (viewType == ITEM_TYPE_NO_SWIPE) return new TickerAdapter.ItemNoSwipeViewHolder(view);
        if (viewType == ITEM_TYPE_RECYCLER_WIDTH) {
            view = getLayoutInflater(parent.getContext()).inflate(R.layout.row_ticker_with_single_delete, parent, false);
            return new TickerAdapter.ItemViewHolderWithRecyclerWidth(view);
        }
        return new TickerAdapter.ItemSwipeWithActionWidthNoSpringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TickerAdapter.ItemBaseViewHolder holder, final int position) {
        final TickerModel tickerModel = this.tickerCollection.get(position);
        //show name
        showNameTickerInView(holder, tickerModel.getName());
        //show symbal
        showSymbolInView(holder, tickerModel.getSymbol());
        //show price
        showPrice_usdInView(holder, tickerModel.getPrice_usd());
        //show price

        //
        holder.view_list_main_content.setOnClickListener(v -> {
            TickerAdapter.this.onItemClicked(tickerModel);
        });
        if (holder instanceof ItemViewHolderWithRecyclerWidth) {
            ItemViewHolderWithRecyclerWidth viewHolder = (ItemViewHolderWithRecyclerWidth) holder;
            viewHolder.view_list_repo_action_delete.setOnClickListener(
                    view -> TickerAdapter.this.onItemRemoved(tickerModel, holder.getAdapterPosition())
            );
        } else if (holder instanceof TickerAdapter.ItemSwipeWithActionWidthViewHolder) {
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
                    view -> TickerAdapter.this.onItemRemoved(tickerModel, holder.getAdapterPosition())
            );
        }

    }
    public void move(int from, int to) {
        TickerModel prev = tickerCollection.remove(from);
        tickerCollection.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setTickerCollection(Collection<TickerModel> tickerCollection) {
        this.validateOrdersCollection(tickerCollection);
        this.tickerCollection = (List<TickerModel>) tickerCollection;
        this.notifyDataSetChanged();
    }

    public void setCurrency_default(String currency_default) {
        this.currency_default = currency_default;
    }

    public void setOnItemClickListener(TickerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateOrdersCollection(Collection<TickerModel> tickerCollection) {
        if (tickerCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    @Override
    public void showNameTickerInView (TickerAdapter.ItemBaseViewHolder viewHolder, String name) {
        viewHolder.ht_name.setText(name);
    }

    @Override
    public void showSymbolInView(TickerAdapter.ItemBaseViewHolder viewHolder, String symbol) {
        viewHolder.ht_symbol.setText(symbol);
    }

    @Override
    public void showPrice_usdInView(TickerAdapter.ItemBaseViewHolder viewHolder, String price_usd) {
        viewHolder.ht_price_usd.setText(price_usd);
    }

    @Override
    public void onItemClicked(TickerModel tickerModel) {
        if (TickerAdapter.this.onItemClickListener != null) {
            TickerAdapter.this.onItemClickListener.onItemClicked(tickerModel);
        }
    }

    @Override
    public void onItemRemoved(TickerModel tickerModel, int position) {

        //
        this.tickerCollection.remove(position);
        notifyItemRemoved(position);
        //
        TickerAdapter.this.onItemClickListener.onItemRemoved();
    }

    @Override
    public void onLinkClicked(TickerModel tickerModel) {
        TickerAdapter.this.onItemClickListener.onLinkClicked(tickerModel);
    }

    public class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ht_name)
        TextView ht_name;
        @Bind(R.id.ht_symbol)
        TextView ht_symbol;
        @Bind(R.id.ht_price_usd)
        TextView ht_price_usd;
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
