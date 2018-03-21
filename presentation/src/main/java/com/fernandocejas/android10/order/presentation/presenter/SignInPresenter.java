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

import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.domain.MetaData;
import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.domain.interactor.PostFirebaseToken;
import com.fernandocejas.android10.order.domain.interactor.SignIn;
import com.fernandocejas.android10.order.domain.interactor.SignIn.Params;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.SignInView;
import com.fernandocejas.android10.order.presentation.view.activity.SignInActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class SignInPresenter implements Presenter {

    private SignInView signInView;
    private final SignIn signInUseCase;
    private final PostFirebaseToken postFirebaseTokenUseCase;

    private boolean is_recall;

    @Inject
    public SignInPresenter(SignIn signInUseCase,
                           PostFirebaseToken postFirebaseTokenUseCase) {
        this.signInUseCase = signInUseCase;
        this.postFirebaseTokenUseCase = postFirebaseTokenUseCase;
    }

    public void setView(@NonNull SignInView view) {
        this.signInView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.signInUseCase.dispose();
        this.postFirebaseTokenUseCase.dispose();
        this.signInView = null;
    }

    public void setIs_recall(boolean is_recall) {
        this.is_recall = is_recall;
    }

    public void callSignIn(String email, String password) {
        this.hideViewRetry();
        this.showViewLoading();
        this.signIn(email, password);
    }

    public void navigateToSignUp() {
        if (signInView.activity() instanceof SignInActivity) {
            ((SignInActivity) signInView.activity()).navigateToSignUp();
        }
    }

    public void navigateToOrderList() {
        if (signInView.activity() instanceof SignInActivity) {
            ((SignInActivity) signInView.activity()).navigateToOrderList();
        }
    }

    public void navigationActivation(String token, String phone, boolean is_recall) {
        if (signInView.activity() instanceof SignInActivity) {
            ((SignInActivity) signInView.activity()).navigateToActivation(token, phone, is_recall);
        }
    }

    private void goBack() {
        if (signInView.activity() instanceof SignInActivity) {
            ((SignInActivity) signInView.activity()).goBack();
        }
    }

    /*

     */

    private void showViewLoading() {
        this.signInView.showLoading();
    }

    private void hideViewLoading() {
        this.signInView.hideLoading();
    }

    private void showViewRetry() {
        this.signInView.showRetry();
    }

    private void hideViewRetry() {
        this.signInView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.signInView.context(),
                errorBundle.getException());
        this.signInView.showError(errorMessage);
    }

    private void signIn(String email, String password) {
        this.signInUseCase.execute(new SignInObserver(), Params.forSignIn(email, password));
    }

    private void saveAccessToken(String token) {
        PreferencesUtility.getInstance(this.signInView.context().getApplicationContext())
                .writeString(PreferencesUtility.PREF_TOKEN, token);
    }

    private void sendRegistrationToServer(String access_token) {

        // TODO: Implement this method to send token to your app server.

        //get fireBase token from Local
        String firebase_token = PreferencesUtility
                .getInstance(signInView.context())
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

    private final class SignInObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            SignInPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            SignInPresenter.this.hideViewLoading();
            SignInPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            SignInPresenter.this.showViewRetry();
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
                    SignInPresenter.this.saveAccessToken(user.getToken());
                    //send firebase to server
                    SignInPresenter.this.sendRegistrationToServer(user.getToken());
                    if (!is_recall) {
                        //go to order list
                        SignInPresenter.this.navigateToOrderList();
                    } else {
                        SignInPresenter.this.goBack();
                    }
                } else if (user.getStatus().equals(Constants.USER_NOT_ACTIVE)) {
                    //go to activation
                    SignInPresenter.this.navigationActivation(user.getToken(), user.getPhone(), is_recall);
                }
            }
        }
    }

}
