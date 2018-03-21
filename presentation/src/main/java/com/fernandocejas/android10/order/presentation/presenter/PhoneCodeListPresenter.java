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
import android.util.Log;

import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.order.presentation.model.CountryModel;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.view.PhoneCodeListView;
import com.fernandocejas.android10.order.presentation.view.activity.SignUpActivity;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class PhoneCodeListPresenter implements Presenter {

    private static final String TAG = "PhoneCodeListPresenter";

    private PhoneCodeListView phoneCodeListView;

    @Inject
    public PhoneCodeListPresenter() {
    }

    public void setView(@NonNull PhoneCodeListView view) {
        this.phoneCodeListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.phoneCodeListView = null;
    }

    public void initialize() {
        loadCountryList();
    }

    public void navigationVerifyPhone() {
        if (phoneCodeListView.activity() instanceof SignUpActivity) {
            ((SignUpActivity) phoneCodeListView.activity()).handleBackPressed();
        }
    }

    public void onCountryClicked(CountryModel countryModel) {
        this.setPhoneCode(countryModel.getDial_code());
        this.navigationVerifyPhone();
    }


    private void loadCountryList() {
        Collection<CountryModel> mCountryList = new ArrayList<>();

        for (Country country : Constants.COUNTRIES.values()) {
            CountryModel countryModel = new CountryModel();
            countryModel.setName(country.getName());
            countryModel.setCode(country.getCode());
            countryModel.setDial_code(country.getDial_code().replace("+", ""));
            mCountryList.add(countryModel);
        }

        PhoneCodeListPresenter.this.showCountryCollectionInView(mCountryList);
    }

    private void setPhoneCode(String phoneCode) {
        Log.d(TAG, phoneCode);
        if (phoneCodeListView.activity() instanceof SignUpActivity) {
            ((SignUpActivity) phoneCodeListView.activity()).setPhoneCode(phoneCode);
        }
    }

    private void showCountryCollectionInView(Collection<CountryModel> countryModelsCollection) {
        this.phoneCodeListView.renderPhoneCodeList(countryModelsCollection);
    }

}
