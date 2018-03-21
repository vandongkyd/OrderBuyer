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

import android.content.Intent;
import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.domain.MetaData;
import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.domain.interactor.ActiveUser;
import com.fernandocejas.android10.order.domain.interactor.PostFirebaseToken;
import com.fernandocejas.android10.order.domain.interactor.ResendActiveCode;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.ActivationView;
import com.fernandocejas.android10.order.presentation.view.activity.ActivationActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ActivationPresenter implements Presenter {

    private ActivationView activationView;

    private final ActiveUser activeUserUseCase;
    private final ResendActiveCode resendActiveCodeUseCase;
    private final PostFirebaseToken postFirebaseTokenUseCase;

    private boolean is_recall;

    @Inject
    public ActivationPresenter(ActiveUser activeUserUseCase,
                               ResendActiveCode resendActiveCodeUseCase,
                               PostFirebaseToken postFirebaseTokenUseCase) {
        this.activeUserUseCase = activeUserUseCase;
        this.resendActiveCodeUseCase = resendActiveCodeUseCase;
        this.postFirebaseTokenUseCase = postFirebaseTokenUseCase;
    }

    public void setView(@NonNull ActivationView view) {
        this.activationView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.activeUserUseCase.dispose();
        this.postFirebaseTokenUseCase.dispose();
        this.activationView = null;
    }

    public void setIs_recall(boolean is_recall) {
        this.is_recall = is_recall;
    }

    public void navigateToOrderList() {
        if (activationView.activity() instanceof ActivationActivity) {
            ((ActivationActivity) activationView.activity()).navigateToOrderList();
        }
    }

    public void callActivateCode(String token, String code) {
        this.hideViewRetry();
        this.showViewLoading();
        this.activateCode(token, code);
    }

    public void callResendActiveCode(String token) {
        this.hideViewRetry();
        this.showViewLoading();
        this.resendActiveCode(token);
    }

    private void showViewLoading() {
        this.activationView.showLoading();
    }

    private void hideViewLoading() {
        this.activationView.hideLoading();
    }

    private void showViewRetry() {
        this.activationView.showRetry();
    }

    private void hideViewRetry() {
        this.activationView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.activationView.context(),
                errorBundle.getException());
        this.activationView.showError(errorMessage);
    }

    private void activateCode(String token, String code) {
        this.activeUserUseCase.execute(new ActivationPresenter.ActivationObserver(), ActiveUser.Params.forActiveUser(token, code));
    }

    private void resendActiveCode(String token) {
        this.resendActiveCodeUseCase.execute(new ResendActiveCodeObserver(), ResendActiveCode.Params.forResendActiveCode(token));
    }

    private void saveAccessToken(String token) {
        PreferencesUtility.getInstance(this.activationView.context())
                .writeString(PreferencesUtility.PREF_TOKEN, token);
    }

    private void sendRegistrationToServer(String access_token) {

        // TODO: Implement this method to send token to your app server.

        //get fireBase token from Local
        String firebase_token = PreferencesUtility
                .getInstance(activationView.context())
                .readString(PreferencesUtility.PREF_FIRE_BASE_TOKEN, null);

        //fetch to server
        if (firebase_token != null && !firebase_token.isEmpty()) {
            fetchFirebaseToken(access_token, firebase_token);
        }
    }

    private void fetchFirebaseToken(String access_token, String firebase_token) {
        postFirebaseTokenUseCase.execute(new PostFirebaseTokenObserver(), PostFirebaseToken.Params.forPostToken(access_token, firebase_token));
    }

    private final class PostFirebaseTokenObserver extends DefaultObserver<MetaData> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(MetaData metaData) {

        }
    }

    private final class ActivationObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            ActivationPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            ActivationPresenter.this.hideViewLoading();
            ActivationPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ActivationPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(User user) {
            if (user != null) {
                if (user.getStatus().equals(Constants.USER_ACTIVE)) {
                    Constants.USER_ID = user.getId();
                    Constants.USER_NAME = user.getName();
                    Constants.USER_AVATAR = user.getAvatar();
                    Constants.USER_PHONE = user.getPhone();
                    Constants.USER_EMAIL = user.getEmail();
                    //save token
                    ActivationPresenter.this.saveAccessToken(user.getToken());
                    //send firebase to server
                    ActivationPresenter.this.sendRegistrationToServer(user.getToken());

                    if (!is_recall) {
                        //go to order list
                        ActivationPresenter.this.navigateToOrderList();
                    } else {
                        activationView.context()
                                .sendBroadcast(new Intent(BaseActivity.BC_EXIT));
                    }
                }
            }
        }
    }

    private final class ResendActiveCodeObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            ActivationPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            ActivationPresenter.this.hideViewLoading();
            ActivationPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ActivationPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(User user) {

        }
    }


}
