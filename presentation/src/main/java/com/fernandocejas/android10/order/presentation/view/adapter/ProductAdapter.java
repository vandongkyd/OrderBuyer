/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
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
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.ProductAdapterView;
import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link ProductModel}.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemBaseViewHolder> implements ProductAdapterView {

    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;
    public static final int ITEM_TYPE_NO_SWIPE = 1003;

    public interface OnItemClickListener {

        void onItemClicked(ProductModel productModel);

        void onItemRemoved();

        void onLinkClicked(ProductModel productModel);
    }

    private List<ProductModel> productsCollection;
    private String currency_default;
    private Context context;

    private OnItemClickListener onItemClickListener;
    private ItemTouchHelperExtension mItemTouchHelperExtension;

    @Inject
    ProductAdapter(Context context) {
        this.context = context;
        this.productsCollection = Collections.emptyList();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH_NO_SPRING;
    }

    @Override
    public int getItemCount() {
        return (this.productsCollection != null) ? this.productsCollection.size() : 0;
    }

    public void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }

    @Override
    public ItemBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater(parent.getContext()).inflate(R.layout.row_product, parent, false);
        if (viewType == ITEM_TYPE_ACTION_WIDTH) return new ItemSwipeWithActionWidthViewHolder(view);
        if (viewType == ITEM_TYPE_NO_SWIPE) return new ItemNoSwipeViewHolder(view);
        if (viewType == ITEM_TYPE_RECYCLER_WIDTH) {
            view = getLayoutInflater(parent.getContext()).inflate(R.layout.row_product_with_single_delete, parent, false);
            return new ItemViewHolderWithRecyclerWidth(view);
        }
        return new ItemSwipeWithActionWidthNoSpringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemBaseViewHolder holder, final int position) {
        final ProductModel productModel = this.productsCollection.get(position);
        //show image
        showThumbnailInView(holder, getImageThumbnail(productModel.getImages()));
        //show name
        showNameProductInView(holder, productModel.getName());
        //show link
        String link = getCommonLink(productModel.getLink());
        showLinkInView(holder, link);
        //show price
        String full_price = getPriceWithSymbolCurrency(productModel.getPrice(), productModel.getCurrency());
        showPriceInView(holder, full_price);
        //show quantity
        showQuantityInView(holder, productModel.getQuantity());
        //
        holder.view_list_main_content.setOnClickListener(v -> {
            ProductAdapter.this.onItemClicked(productModel);
        });
        holder.tv_where_to_buy.setOnClickListener(v -> {
            ProductAdapter.this.onLinkClicked(productModel);
        });
        if (holder instanceof ItemViewHolderWithRecyclerWidth) {
            ItemViewHolderWithRecyclerWidth viewHolder = (ItemViewHolderWithRecyclerWidth) holder;
            viewHolder.view_list_repo_action_delete.setOnClickListener(
                    view -> ProductAdapter.this.onItemRemoved(productModel, holder.getAdapterPosition())
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
                    view -> ProductAdapter.this.onItemRemoved(productModel, holder.getAdapterPosition())
            );
        }

    }

    public void move(int from, int to) {
        ProductModel prev = productsCollection.remove(from);
        productsCollection.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setProductsCollection(Collection<ProductModel> productsCollection) {
        this.validateOrdersCollection(productsCollection);
        this.productsCollection = (List<ProductModel>) productsCollection;
        this.notifyDataSetChanged();
    }

    public void setCurrency_default(String currency_default) {
        this.currency_default = currency_default;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateOrdersCollection(Collection<ProductModel> productsCollection) {
        if (productsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    private String getImageThumbnail(Collection<ImageModel> imageModels) {
        String url = null;
        if (imageModels != null && imageModels.size() > 0) {
            try {
                url = ((List<ImageModel>) imageModels).get(0).getImage();
            } catch (Exception ex) {
            }
        }
        return url;
    }

    private String getCommonLink(String link) {
        try {
            String l = link.replace("https://", "");
            l = l.split("/")[0];
            return l;
        } catch (Exception ex) {
        }
        return link;
    }

    private String getPriceWithSymbolCurrency(String amount, String currency) {
        String full_price = "";
        if (currency == null || currency.isEmpty()) {
            currency = currency_default;
        }
        String symbol = Utils.getCurrencySymbol(currency);
        full_price = symbol + " " + amount;
        return full_price;
    }

    @Override
    public void showNameProductInView(ItemBaseViewHolder viewHolder, String name) {
        viewHolder.tv_name.setText(name);
    }

    @Override
    public void showThumbnailInView(ItemBaseViewHolder viewHolder, String url) {
        Glide.with(context).load(url).into(viewHolder.imv_thumb);
    }

    @Override
    public void showLinkInView(ItemBaseViewHolder viewHolder, String link) {
        viewHolder.tv_where_to_buy.setText(link);
    }

    @Override
    public void showPriceInView(ItemBaseViewHolder viewHolder, String price) {
        viewHolder.tv_price.setText(price);
    }

    @Override
    public void showQuantityInView(ItemBaseViewHolder viewHolder, String quantity) {
        viewHolder.tv_quantity.setText("x " + quantity);
    }

    @Override
    public void onItemClicked(ProductModel productModel) {
        if (ProductAdapter.this.onItemClickListener != null) {
            ProductAdapter.this.onItemClickListener.onItemClicked(productModel);
        }
    }

    @Override
    public void onItemRemoved(ProductModel productModel, int position) {
        Constants.PRODUCTS.remove(productModel.getLink());
        //
        this.productsCollection.remove(position);
        notifyItemRemoved(position);
        //
        ProductAdapter.this.onItemClickListener.onItemRemoved();
    }

    @Override
    public void onLinkClicked(ProductModel productModel) {
        ProductAdapter.this.onItemClickListener.onLinkClicked(productModel);
    }

    public class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imv_thumb)
        ImageView imv_thumb;
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_where_to_buy)
        TextView tv_where_to_buy;
        @Bind(R.id.tv_price)
        TextView tv_price;
        @Bind(R.id.tv_quantity)
        TextView tv_quantity;
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
