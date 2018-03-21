/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.view.adapter.ChatMessageAdapter;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface ChatMessageAdapterView {

    void showAvatarInView(ChatMessageAdapter.MessageViewHolder viewHolder, String url);

    void showMessageInView(ChatMessageAdapter.MessageViewHolder viewHolder, String message);

    void showTimeInView(ChatMessageAdapter.MessageViewHolder viewHolder, String time);

}

