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

import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.order.domain.MetaData;
import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.domain.interactor.GetCountryList;
import com.fernandocejas.android10.order.domain.interactor.PostFirebaseToken;
import com.fernandocejas.android10.order.domain.interactor.RefreshAccessToken;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.SplashScreenView;
import com.fernandocejas.android10.order.presentation.view.activity.SplashScreenActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class SplashScreenPresenter implements Presenter {

    private SplashScreenView splashScreenView;

    private final RefreshAccessToken refreshAccessTokenUseCase;
    private final PostFirebaseToken postFirebaseTokenUseCase;

    private final GetCountryList getCountryListUseCase;

    @Inject
    public SplashScreenPresenter(RefreshAccessToken refreshAccessToken,
                                 GetCountryList getCountryListUseCase,
                                 PostFirebaseToken postFirebaseTokenUseCase) {
        this.refreshAccessTokenUseCase = refreshAccessToken;
        this.getCountryListUseCase = getCountryListUseCase;
        this.postFirebaseTokenUseCase = postFirebaseTokenUseCase;
    }

    public void setView(@NonNull SplashScreenView view) {
        this.splashScreenView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.refreshAccessTokenUseCase.dispose();
        this.getCountryListUseCase.dispose();
        this.postFirebaseTokenUseCase.dispose();
        this.splashScreenView = null;
    }

    public void initialize() {
        if (checkConnection()) {
            String access_token = PreferencesUtility.getInstance(splashScreenView.context())
                    .readString(PreferencesUtility.PREF_TOKEN, null);
            if (access_token == null || access_token.isEmpty()) {
                SplashScreenPresenter.this.navigateToSignIn();
            } else {
                loadUserInformation(access_token);
            }
            loadCountryList();
        }
    }

    private void showViewLoading() {
        this.splashScreenView.showLoading();
    }

    private void hideViewLoading() {
        this.splashScreenView.hideLoading();
    }

    private void showViewRetry() {
        this.splashScreenView.showRetry();
    }

    private void hideViewRetry() {
        this.splashScreenView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
//        String errorMessage = ErrorMessageFactory.create(this.splashScreenView.activity(),
//                errorBundle.getException());
//        this.splashScreenView.showError(errorMessage);
        SplashScreenPresenter.this.navigateToSignIn();
    }

    /**
     * Check network connection
     */
    private boolean checkConnection() {
        if (Utils.isThereInternetConnection(splashScreenView.context()) == false) {
            splashScreenView.showNetWorkError();
        } else {
            if (splashScreenView.activity() instanceof SplashScreenActivity) {
                splashScreenView.dismissNetWorkError();
                return true;
            }
        }
        return false;
    }

    private void loadUserInformation(String token) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserInformation(token);
    }

    private void navigateToSignIn() {
        if (splashScreenView.activity() instanceof SplashScreenActivity) {
            ((SplashScreenActivity) splashScreenView.activity()).navigateToSignIn();
        }
    }


    private void navigateToOrderList() {
        if (splashScreenView.activity() instanceof SplashScreenActivity) {
            ((SplashScreenActivity) splashScreenView.activity()).navigateToOrderList();
        }
    }

    private void getUserInformation(String token) {
        refreshAccessTokenUseCase.execute(new RefreshAccessTokenObserver(),
                RefreshAccessToken.Params.forRetrieveUserInformation(token));
    }

    private void saveAccessToken(String token) {
        PreferencesUtility.getInstance(this.splashScreenView.context().getApplicationContext())
                .writeString(PreferencesUtility.PREF_TOKEN, token);
    }

    private void loadCountryList() {
        getCountryListUseCase.execute(new CountryListObserver(), null);
    }

    private void sendRegistrationToServer(String access_token) {

        // TODO: Implement this method to send token to your app server.

        //get fireBase token from Local
        String firebase_token = PreferencesUtility
                .getInstance(splashScreenView.context())
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

    private final class RefreshAccessTokenObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            SplashScreenPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            SplashScreenPresenter.this.hideViewLoading();
            SplashScreenPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            SplashScreenPresenter.this.showViewRetry();
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
                    SplashScreenPresenter.this.saveAccessToken(user.getToken());
                    //send firebase to server
                    SplashScreenPresenter.this.sendRegistrationToServer(user.getToken());
                    //go to order list
                    SplashScreenPresenter.this.navigateToOrderList();
                }
            }
        }
    }

    private final class CountryListObserver extends DefaultObserver<List<Country>> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(List<Country> countries) {
            Constants.COUNTRIES = new HashMap<>();
            for (Country country : countries) {
                Constants.COUNTRIES.put(country.getCurrency_code(), country);
            }
        }
    }
}
