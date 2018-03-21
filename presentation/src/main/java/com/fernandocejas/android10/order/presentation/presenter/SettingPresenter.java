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

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.interactor.GetAddressList;
import com.fernandocejas.android10.order.presentation.mapper.AddressModelDataMapper;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.SettingView;
import com.fernandocejas.android10.order.presentation.view.activity.SettingActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class SettingPresenter implements Presenter {

    private SettingView settingView;

    private GetAddressList getAddressListUseCase;
    private AddressModelDataMapper addressModelDataMapper;
    private List<Address> addresses;

    @Inject
    public SettingPresenter(GetAddressList getAddressListUseCase, AddressModelDataMapper addressModelDataMapper) {
        this.getAddressListUseCase = getAddressListUseCase;
        this.addressModelDataMapper = addressModelDataMapper;
    }

    public void setView(@NonNull SettingView view) {
        this.settingView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getAddressListUseCase.dispose();
        this.settingView = null;
    }

    private void showViewLoading() {
        this.settingView.showLoading();
    }

    private void hideViewLoading() {
        this.settingView.hideLoading();
    }

    private void showViewRetry() {
        this.settingView.showRetry();
    }

    private void hideViewRetry() {
        this.settingView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.settingView.context(),
                errorBundle.getException());
        this.settingView.showError(errorMessage);
    }

    private void showAddressCollection() {
        if (addresses != null && !addresses.isEmpty()) {
            this.settingView.renderAddresses(addressModelDataMapper.transform(this.addresses));
        }
    }

    public void goBack() {
        navigateToOrderList();
    }

    public void goToAddAddress() {
        navigateToAddAddress();
    }

    public void loadUserInfo() {
        getUserInfo();
    }

    public void loadAddress() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getAddresses();
    }

    public void addAddress(Address address) {
        try {
            this.addresses.add(address);
            showAddressCollection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void navigateToOrderList() {
        if (this.settingView.activity() instanceof SettingActivity) {
            ((SettingActivity) settingView.activity()).navigateToOrderList();
        }
    }

    private void navigateToAddAddress() {
        if (this.settingView.activity() instanceof SettingActivity) {
            ((SettingActivity) settingView.activity()).navigateToAddAddress();
        }
    }

    private void getAddresses() {
        String token = PreferencesUtility.getInstance(settingView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        getAddressListUseCase.execute(new GetAddressListObserver(), GetAddressList.Params.forAddress(token));

    }

    private void getUserInfo() {
        String avatar = Constants.USER_AVATAR;
        String name = Constants.USER_NAME;
        String phone = Constants.USER_PHONE;
        String email = Constants.USER_EMAIL;
        //show avatar
        this.settingView.showAvatarInView(avatar);
        //show name
        this.settingView.showNameInView(name);
        //show phone
        this.settingView.showPhoneInView(phone);
        //show email
        this.settingView.showEmailInView(email);
    }

    private final class GetAddressListObserver extends DefaultObserver<List<Address>> {

        @Override
        public void onComplete() {
            SettingPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            SettingPresenter.this.hideViewLoading();
            SettingPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            SettingPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Address> addresses) {
            SettingPresenter.this.addresses = addresses;
            SettingPresenter.this.showAddressCollection();
        }
    }
}
