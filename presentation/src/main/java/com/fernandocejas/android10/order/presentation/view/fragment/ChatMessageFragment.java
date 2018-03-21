/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.MessageModel;
import com.fernandocejas.android10.order.presentation.presenter.ChatMessagePresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.ChatMessageView;
import com.fernandocejas.android10.order.presentation.view.adapter.ChatMessageAdapter;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseWithMediaPickerFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class ChatMessageFragment extends BaseWithMediaPickerFragment implements ChatMessageView {

    @Inject
    ChatMessagePresenter chatMessagePresenter;
    @Inject
    ChatMessageAdapter chatMessageAdapter;
    @Bind(R.id.rv_messages)
    RecyclerView rv_messages;
    @Bind(R.id.edt_message)
    EditText edt_message;
    @Bind(R.id.toolbar_title_hint)
    TextView toolbar_title_hint;
    @Bind(R.id.toolbar_avatar)
    ImageView toolbar_avatar;

    private ProgressDialog progressDialog;
    private ChatMessageAdapter.OnItemClickListener onItemChatMessageClickListener = messageModel -> ChatMessageFragment.this.onItemChatMessageClick(messageModel);

    public ChatMessageFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(OrderComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_chat_message, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecycleView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.chatMessagePresenter.setView(this);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            String order_id = arguments.getString("args_order_id");
            String provider_id = arguments.getString("args_provider_id");
            String user_id = arguments.getString("args_user_id");
            String provider_avatar = arguments.getString("args_provider_avatar");
            this.chatMessagePresenter.setOrder_id(order_id);
            this.chatMessagePresenter.setProvider_id(provider_id);
            this.chatMessagePresenter.setUser_id(user_id);
            this.chatMessageAdapter.setAvatarProvider(provider_avatar);
            //
            this.showOrderNumberInView(order_id);
            this.showAvatarInView(provider_avatar);
            //
            this.chatMessagePresenter.registerChatMessage();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.chatMessagePresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.chatMessagePresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.chatMessagePresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.chatMessagePresenter = null;
    }

    @Override
    public void showLoading() {
        if (this.progressDialog == null) {
            this.progressDialog = DialogFactory.createProgressDialog(activity(), R.string.processing);
        }
        this.progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(activity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.chatMessagePresenter.goBack();
    }

    @Override
    @OnClick(R.id.btn_pick_photo)
    public void onPickPhotoClicked() {
        onPhotoClicked();
    }

    @Override
    public void onPhotoPicked(String path) {
        this.chatMessagePresenter.sendMessage(path, ChatMessagePresenter.MESSAGE_TYPE_MEDIA);
    }

    @Override
    public void onVideoPicked(String path) {

    }

    @Override
    @OnClick(R.id.btn_send)
    public void onSendClicked() {
        if (edt_message.getText() == null || edt_message.getText().toString().isEmpty()) {
            return;
        }
        this.chatMessagePresenter.sendMessage(edt_message.getText().toString(), ChatMessagePresenter.MESSAGE_TYPE_TEXT);
        edt_message.setText(null);
        // hide virtual keyboard
        InputMethodManager imm = (InputMethodManager) activity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_message.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }

    @Override
    public void renderMessageList(List<MessageModel> messageModels) {
        this.chatMessageAdapter.setMessageModels(messageModels);
        //scroll view to bottom
        if (messageModels != null && messageModels.size() > 0) {
            rv_messages.scrollToPosition(messageModels.size() - 1);
        }
    }

    @Override
    public void onItemChatMessageClick(MessageModel messageModel) {

    }

    @Override
    public void showOrderNumberInView(String text) {
        toolbar_title_hint.setText("#" + text);
    }

    @Override
    public void showAvatarInView(String url) {
        loadImageFromUrl(context(), toolbar_avatar, url, true, true);
    }

    private void setupRecycleView() {

        this.chatMessageAdapter.setOnItemClickListener(onItemChatMessageClickListener);
        this.chatMessageAdapter.setAvatarProvider(chatMessagePresenter.getProvider_avatar());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context());
        // linearLayoutManager.setReverseLayout(true); // setReverseLayout will change the order of the elements added by the Adapter
        linearLayoutManager.setStackFromEnd(true);//setStackFromEnd will set the view to show the last element, the layout direction will remain the same
        this.rv_messages.setLayoutManager(linearLayoutManager);
        this.rv_messages.setAdapter(chatMessageAdapter);
    }

    private void loadImageFromUrl(Context context, ImageView view, String url, boolean isCircle, boolean hasDefault) {
        if (url == null || url.isEmpty()) {
            if (hasDefault) {
                //show default avatar if we don't have url to show
                Glide.with(context)
                        .load(R.drawable.default_avatar)
                        .asBitmap()
                        .into(new BitmapImageViewTarget(view) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                view.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
            return;
        }
        if (isCircle) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(new BitmapImageViewTarget(view) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            view.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } else {
            Glide.with(context)
                    .load(url)
                    .into(view);
        }
    }
}
