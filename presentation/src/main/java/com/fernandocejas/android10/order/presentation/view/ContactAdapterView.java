package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.view.adapter.ContactAdapter;

/**
 * Created by vandongluong on 3/9/18.
 */

public interface ContactAdapterView {
    void showNameInView(ContactAdapter.ItemBaseViewHolder viewHolder, String name);

    void showEmailInView(ContactAdapter.ItemBaseViewHolder viewHolder, String email);

    void showAddressCInView(ContactAdapter.ItemBaseViewHolder viewHolder, String address);
    void showMobileInView(ContactAdapter.ItemBaseViewHolder viewHolder, String mobile);
}
