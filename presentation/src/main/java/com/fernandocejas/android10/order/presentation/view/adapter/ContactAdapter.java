package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.ContactModel;
import com.fernandocejas.android10.order.presentation.model.TickerModel;
import com.fernandocejas.android10.order.presentation.view.ContactAdapterView;
import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by vandongluong on 3/15/18.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ItemBaseViewHolder> implements ContactAdapterView {


    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;
    public static final int ITEM_TYPE_NO_SWIPE = 1003;


    private List<ContactModel> contactModelcollection;
    private String currency_default;
    private Context context;
    private ItemTouchHelperExtension mItemTouchHelperExtension;

    @Inject
    ContactAdapter(Context context) {
        this.context = context;
        this.contactModelcollection = Collections.emptyList();
    }
    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH_NO_SPRING;
    }




    @Override
    public int getItemCount() {
        return (this.contactModelcollection != null) ? this.contactModelcollection.size() : 0;
    }
    public void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }
    @Override
    public ContactAdapter.ItemBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater(parent.getContext()).inflate(R.layout.row_contact, parent, false);
        if (viewType == ITEM_TYPE_ACTION_WIDTH) return new ContactAdapter.ItemSwipeWithActionWidthViewHolder(view);
        if (viewType == ITEM_TYPE_NO_SWIPE) return new ContactAdapter.ItemNoSwipeViewHolder(view);
        if (viewType == ITEM_TYPE_RECYCLER_WIDTH) {
            view = getLayoutInflater(parent.getContext()).inflate(R.layout.row_ticker_with_single_delete, parent, false);
            return new ContactAdapter.ItemViewHolderWithRecyclerWidth(view);
        }
        return new ContactAdapter.ItemSwipeWithActionWidthNoSpringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ItemBaseViewHolder holder, int position) {
        final ContactModel contactModel = this.contactModelcollection.get(position);
        showNameInView(holder,contactModel.getName());
        showEmailInView(holder , contactModel.getEmail());
        showAddressCInView(holder , contactModel.getAddress());
        showMobileInView(holder , contactModel.getMobile());
    }
    public void move(int from, int to) {
        ContactModel prev = contactModelcollection.remove(from);
        contactModelcollection.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setContactCollection(Collection<ContactModel> contactCollection) {
        this.validateOrdersCollection(contactCollection);
        this.contactModelcollection = (List<ContactModel>) contactCollection;
        this.notifyDataSetChanged();
    }

    private void validateOrdersCollection(Collection<ContactModel> contactCollection) {
        if (contactCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    @Override
    public void showNameInView(ContactAdapter.ItemBaseViewHolder viewHolder, String name) {
        viewHolder.ht_name1.setText(name);
    }

    @Override
    public void showEmailInView(ContactAdapter.ItemBaseViewHolder viewHolder, String email) {
        viewHolder.ht_email.setText(email);
    }

    @Override
    public void showAddressCInView(ContactAdapter.ItemBaseViewHolder viewHolder, String address) {
        viewHolder.ht_address1.setText(address);
    }

    @Override
    public void showMobileInView(ContactAdapter.ItemBaseViewHolder viewHolder, String mobile) {
        viewHolder.ht_mobile.setText(mobile);
    }

    public class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ht_name1)
        TextView ht_name1;
        @Bind(R.id.ht_email)
        TextView ht_email;
        @Bind(R.id.ht_address1)
        TextView ht_address1;
        @Bind(R.id.ht_mobile)
        TextView ht_mobile;
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
