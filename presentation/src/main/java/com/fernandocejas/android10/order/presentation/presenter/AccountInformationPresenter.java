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
import android.webkit.URLUtil;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.fernandocejas.android10.BuildConfig;
import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.domain.interactor.ChangePassword;
import com.fernandocejas.android10.order.domain.interactor.RetrieveUserInformation;
import com.fernandocejas.android10.order.domain.interactor.UpdateUserInfo;
import com.fernandocejas.android10.order.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.order.presentation.utils.AWSHelper;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.AccountInformationView;
import com.fernandocejas.android10.order.presentation.view.activity.AccountInformationActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.io.File;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class AccountInformationPresenter implements Presenter {

    @Inject
    AWSHelper awsHelper;

    private AccountInformationView accountInformationView;

    private final RetrieveUserInformation retrieveUserInformationUseCase;
    private final ChangePassword changePasswordUseCase;
    private final UpdateUserInfo updateUserInfoUseCase;
    private final UserModelDataMapper userModelDataMapper;
    private User user;

    @Inject
    public AccountInformationPresenter(
            UserModelDataMapper userModelDataMapper,
            RetrieveUserInformation retrieveUserInformationUseCase,
            ChangePassword changePasswordUseCase,
            UpdateUserInfo updateUserInfoUseCase) {
        this.userModelDataMapper = userModelDataMapper;
        this.retrieveUserInformationUseCase = retrieveUserInformationUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
        this.updateUserInfoUseCase = updateUserInfoUseCase;
    }

    public void setView(@NonNull AccountInformationView view) {
        this.accountInformationView = view;
    }

    private void setUser(User user) {
        if (user == null) {
            return;
        }
        this.user = user;
        //update local
        {
            Constants.USER_ID = user.getId();
            Constants.USER_NAME = user.getName();
            Constants.USER_AVATAR = user.getAvatar();
            Constants.USER_PHONE = user.getPhone();
            Constants.USER_EMAIL = user.getEmail();
        }
        this.accountInformationView.showUserInView(userModelDataMapper.transform(this.user));
    }

    public void setAvatar(String avatar) {
        if (user == null) {
            return;
        }
        this.user.setAvatar(avatar);
        this.accountInformationView.showAvatarInView(avatar);
    }

    public void loadUser() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserInfo();
    }

    public void fetchChangePassword(String old_password, String password) {
        this.hideViewRetry();
        this.showViewLoading();
        this.changePassword(old_password, password);
    }

    public void fetchUpdateUserInfo(String name, String phone) {
        this.hideViewRetry();
        this.showViewLoading();
        this.updateUserInfo(name, phone);
    }

    private void changePassword(String old_password, String password) {
        String access_token = PreferencesUtility.getInstance(this.accountInformationView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.changePasswordUseCase.execute(new ChangePasswordObserver(), ChangePassword.Params.forChangePassword(access_token, old_password, password));
    }

    private void updateUserInfo(String name, String phone) {
        String access_token = PreferencesUtility.getInstance(this.accountInformationView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        //upload avatar to S3
        try {
            if (this.user.getAvatar() == null || this.user.getAvatar().isEmpty()) {
                if (!URLUtil.isNetworkUrl(this.user.getAvatar())) {
                    String filePath = this.user.getAvatar();
                    String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
                    this.user.setAvatar(BuildConfig.AVATAR_HOST + filename);
                    //
                    uploadPhotoToAWS(this.accountInformationView.context(), filename, new File(filePath));
                }
            }
        } catch (Exception ex) {
        }
        //
        this.updateUserInfoUseCase.execute(new UpdateUserInfoObserver(), UpdateUserInfo.Params.forUpdateUserInfo(access_token, name, phone, user.getAvatar()));
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.accountInformationView = null;
    }

    private void showViewLoading() {
        this.accountInformationView.showLoading();
    }

    private void hideViewLoading() {
        this.accountInformationView.hideLoading();
    }

    private void showViewRetry() {
        this.accountInformationView.showRetry();
    }

    private void hideViewRetry() {
        this.accountInformationView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.accountInformationView.context(),
                errorBundle.getException());
        this.accountInformationView.showError(errorMessage);
    }

    private void getUserInfo() {
        String access_token = PreferencesUtility.getInstance(this.accountInformationView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.retrieveUserInformationUseCase.execute(new RetrieveAccessTokenObserver(), RetrieveUserInformation.Params.forRetrieveUserInformation(access_token));
    }

    public void navigateToOrderList() {
        if (accountInformationView.activity() instanceof AccountInformationActivity) {
            ((AccountInformationActivity) accountInformationView.activity()).navigateToOrderList();
        }
    }

    public void goBack() {
        navigateToOrderList();
    }

    private final class RetrieveAccessTokenObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            AccountInformationPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            AccountInformationPresenter.this.hideViewLoading();
            AccountInformationPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            AccountInformationPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(User user) {
            if (user != null) {
                if (user.getStatus().equals(Constants.USER_ACTIVE)) {
                    AccountInformationPresenter.this.setUser(user);
                }
            }
        }
    }

    private void saveAccessToken(String token) {
        PreferencesUtility.getInstance(this.accountInformationView.context())
                .writeString(PreferencesUtility.PREF_TOKEN, token);
    }

    private void showPasswordChangedSuccess() {
        this.accountInformationView.showPasswordChangedSuccess();
    }

    private void showUserInfoChangedSuccess() {
        this.accountInformationView.showUpdateUserInfoSuccess();
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

    private final class ChangePasswordObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            AccountInformationPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            AccountInformationPresenter.this.hideViewLoading();
            AccountInformationPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            AccountInformationPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(User user) {
            if (user != null) {
                if (user.getStatus().equals(Constants.USER_ACTIVE)) {
                    AccountInformationPresenter.this.setUser(user);
                    //save token
                    AccountInformationPresenter.this.saveAccessToken(user.getToken());
                    //
                    AccountInformationPresenter.this.showPasswordChangedSuccess();
                }
            }
        }
    }

    private final class UpdateUserInfoObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            AccountInformationPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            AccountInformationPresenter.this.hideViewLoading();
            AccountInformationPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            AccountInformationPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(User user) {
            if (user != null) {
                if (user.getStatus().equals(Constants.USER_ACTIVE)) {
                    AccountInformationPresenter.this.setUser(user);
                    //save token
                    AccountInformationPresenter.this.saveAccessToken(user.getToken());
                    //
                    AccountInformationPresenter.this.showUserInfoChangedSuccess();
                }
            }
        }
    }

}
