/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.order.presentation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.fernandocejas.android10.BuildConfig;
import com.fernandocejas.android10.order.domain.Message;
import com.fernandocejas.android10.order.domain.interactor.SendMessage;
import com.fernandocejas.android10.order.presentation.model.MessageModel;
import com.fernandocejas.android10.order.presentation.utils.AWSHelper;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.ChatMessageView;
import com.fernandocejas.android10.order.presentation.view.activity.ChatMessageActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ChatMessagePresenter implements Presenter {

    public static final int MESSAGE_TYPE_TEXT = 101;
    public static final int MESSAGE_TYPE_MEDIA = 102;

    @Inject
    AWSHelper awsHelper;

    private ChatMessageView chatMessageView;

    private final SendMessage sendMessageUseCase;

    private String order_id;
    private String provider_id;
    private String user_id;
    private String provider_avatar;

    @Inject
    public ChatMessagePresenter(SendMessage sendMessageUseCase) {
        this.sendMessageUseCase = sendMessageUseCase;
    }

    public void setView(@NonNull ChatMessageView view) {
        this.chatMessageView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.chatMessageView = null;
    }

    private void showViewLoading() {
        this.chatMessageView.showLoading();
    }

    private void hideViewLoading() {
        this.chatMessageView.hideLoading();
    }

    private void showViewRetry() {
        this.chatMessageView.showRetry();
    }

    private void hideViewRetry() {
        this.chatMessageView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.chatMessageView.context(),
                errorBundle.getException());
        this.chatMessageView.showError(errorMessage);
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setProvider_avatar(String provider_avatar) {
        this.provider_avatar = provider_avatar;
    }

    public String getProvider_avatar() {
        return provider_avatar;
    }

    public void goBack() {
        if (this.chatMessageView.activity() instanceof ChatMessageActivity) {
            ((ChatMessageActivity) this.chatMessageView.activity()).navigateToOrderDetail();
        }
    }

    public void renderMessageList(List<MessageModel> messageModels) {
        try {
            if (messageModels == null) {
                return;
            }
            this.chatMessageView.renderMessageList(messageModels);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage(String message, int type) {
        switch (type) {
            case MESSAGE_TYPE_TEXT:
                ChatMessagePresenter.this.fetchMessage(message);
                break;
            case MESSAGE_TYPE_MEDIA:
                String filename = message.substring(message.lastIndexOf("/") + 1);
                uploadPhotoToAWS(this.chatMessageView.context(), filename, new File(message));
                ChatMessagePresenter.this.fetchMessage(BuildConfig.AVATAR_HOST + filename);
                break;
            default:
                break;

        }
    }

    public void registerChatMessage() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                populateData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Log.d(TAG, order_id + "_" + provider_id + "_" + user_id);
        ref.child("messages").child(order_id + "_" + provider_id + "_" + user_id).addValueEventListener(valueEventListener);
    }

    private void postMessage(String message) throws Exception {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(message);

        messageModel.setDatetime(Utils.getDateTime(new Date().getTime(), "yyyy-MM-dd HH:mm:ss"));
        messageModel.setOrder(Long.valueOf(order_id));
        messageModel.setSender(Long.valueOf(Constants.USER_ID));
        messageModel.setReceiver(Long.valueOf(provider_id));

        ref.child("messages").child(order_id + "_" + provider_id + "_" + user_id)
                .push()
                .setValue(messageModel, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        ChatMessagePresenter.this.chatMessageView.showError(databaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                });
    }

    private void populateData(DataSnapshot dataSnapshot) {
        List<MessageModel> modelCollection = new ArrayList<>();
        for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
            try {
                MessageModel messageModel = categorySnapshot.getValue(MessageModel.class);
                modelCollection.add(messageModel);
            } catch (Exception ex) {
            }
        }
        if (modelCollection != null && !modelCollection.isEmpty()) {
            ChatMessagePresenter.this.renderMessageList(modelCollection);
        }
    }

    private void fetchMessage(String message) {
        String token = PreferencesUtility.getInstance(chatMessageView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        sendMessageUseCase.execute(new SendMessageObserver(), SendMessage.Params.forSendMessage(token, order_id, provider_id, message, "1"));
    }

    private void uploadPhotoToAWS(Context context, String filename, File file) {
        Log.d("s3_upload_path", BuildConfig.AVATAR_HOST + filename);
        awsHelper.getTransferUtility(context).upload(BuildConfig.BUCKET_NAME, filename, file)
                .setTransferListener(new TransferListener() {
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        Log.d("s3", "onStateChanged");
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        Log.d("s3", "onProgressChanged");
                    }

                    @Override
                    public void onError(int id, Exception ex) {
                        Log.d("s3", "onError");
                    }
                });
    }

    private final class SendMessageObserver extends DefaultObserver<Message> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            ChatMessagePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(Message order) {

        }
    }

}
