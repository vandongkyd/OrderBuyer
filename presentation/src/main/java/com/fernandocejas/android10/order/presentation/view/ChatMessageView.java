/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.MessageModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface ChatMessageView extends LoadDataView{

    void onBackClicked();

    void onPickPhotoClicked();

    void onSendClicked();

    void renderMessageList(List<MessageModel> messageModels);

    void onItemChatMessageClick(MessageModel messageModel);

    void showOrderNumberInView(String text);

    void showAvatarInView(String url);
}

