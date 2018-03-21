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

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.GoogleGeo;
import com.fernandocejas.android10.order.domain.interactor.AddAddress;
import com.fernandocejas.android10.order.domain.interactor.GetGoogleGeoByLocation;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.AddAddressView;
import com.fernandocejas.android10.order.presentation.view.activity.AddAddressActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class AddAddressPresenter implements Presenter,
        GoogleMap.OnCameraIdleListener {

    private final String TAG = "AddAddressPresenter";

    private final GetGoogleGeoByLocation getGoogleGeoByLocationUseCase;
    private final AddAddress addAddressUseCase;
    private AddAddressView addAddressView;
    private GoogleMap googleMap;
    private GoogleGeo googleGeo;

    @Inject
    public AddAddressPresenter(GetGoogleGeoByLocation getGoogleGeoByLocationUseCase, AddAddress addAddressUseCase) {
        this.getGoogleGeoByLocationUseCase = getGoogleGeoByLocationUseCase;
        this.addAddressUseCase = addAddressUseCase;
    }

    public void setView(@NonNull AddAddressView view) {
        this.addAddressView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getGoogleGeoByLocationUseCase.dispose();
        this.addAddressUseCase.dispose();
        this.addAddressView = null;
    }

    private void showViewLoading() {
        this.addAddressView.showLoading();
    }

    private void hideViewLoading() {
        this.addAddressView.hideLoading();
    }

    private void showViewRetry() {
        this.addAddressView.showRetry();
    }

    private void hideViewRetry() {
        this.addAddressView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.addAddressView.context(),
                errorBundle.getException());
        this.addAddressView.showError(errorMessage);
    }

    public void setGoogleGeo(GoogleGeo googleGeo) {
        this.googleGeo = googleGeo;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        if (googleMap != null) {
            this.googleMap = googleMap;
            this.googleMap = googleMap;
            if (ActivityCompat.checkSelfPermission(this.addAddressView.activity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this.addAddressView.activity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            } else {
                this.googleMap.setMyLocationEnabled(true);
            }
            this.googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            this.googleMap.getUiSettings().setCompassEnabled(true);
            this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            this.googleMap.getUiSettings().setRotateGesturesEnabled(true);
            this.googleMap.getUiSettings().setScrollGesturesEnabled(true);
            this.googleMap.getUiSettings().setTiltGesturesEnabled(true);
            this.googleMap.getUiSettings().setZoomGesturesEnabled(true);
            this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            this.googleMap.setIndoorEnabled(false);
            this.googleMap.setTrafficEnabled(true);
            //todo: listeners
            {
                this.googleMap.setOnCameraIdleListener(this);
            }
            this.addAddressView.loadLastLocation();
        }
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void loadGoogleGeoByLocation(LatLng latLng) {
//        this.getGoogleGeoByLocationUseCase.dispose();
        this.getGoogleGeoByLocation(latLng);
    }

    public void submitAddress() {
        if (this.googleGeo != null) {
            this.hideViewRetry();
            this.showViewLoading();
            this.addAddAddress(this.googleGeo);
        }
    }

    private LatLng getCenterPoint() {
        if (this.googleMap != null) {
            return this.googleMap.getCameraPosition().target;
        }
        return null;
    }

    private void getGoogleGeoByLocation(LatLng latLng) {
        Log.d(TAG, latLng.latitude + "," + latLng.longitude);
        this.getGoogleGeoByLocationUseCase.execute(new GetGoogleGeoByLocationObserver(), GetGoogleGeoByLocation.Params.forGeoCode(latLng.latitude + "", latLng.longitude + ""));
    }

    private void addAddAddress(GoogleGeo googleGeo) {
        String access_token = PreferencesUtility.getInstance(this.addAddressView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        this.addAddressUseCase.execute(new AddAddressObserver(),
                AddAddress.Params.forAddAddress(access_token,
                        googleGeo.getFormatted_address(),
                        googleGeo.getCity(),
                        googleGeo.getPostcode(),
                        googleGeo.getCountry(),
                        googleGeo.getCountry_code(),
                        googleGeo.getLat(),
                        googleGeo.getLng()
                ));
    }

    @Override
    public void onCameraIdle() {
        LatLng latLng = getCenterPoint();
        if (latLng != null) {
            loadGoogleGeoByLocation(latLng);
        }
    }

    public void goBack() {
        navigateToSetting();
    }

    private void navigateToSetting() {
        if (this.addAddressView.activity() instanceof AddAddressActivity) {
            ((AddAddressActivity) addAddressView.activity()).navigateToSetting();
        }
    }

    private final class GetGoogleGeoByLocationObserver extends DefaultObserver<List<GoogleGeo>> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            AddAddressPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(List<GoogleGeo> googleGeos) {
            if (googleGeos != null && !googleGeos.isEmpty()) {

                AddAddressPresenter.this.setGoogleGeo(googleGeos.get(0));

                AddAddressPresenter.this.addAddressView.showAddressInView(
                        AddAddressPresenter.this.googleGeo.getFormatted_address());
            }
        }
    }

    private final class AddAddressObserver extends DefaultObserver<Address> {

        @Override
        public void onComplete() {
            AddAddressPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            AddAddressPresenter.this.hideViewLoading();
            AddAddressPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            AddAddressPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Address address) {
            Intent intent = new Intent(BaseActivity.BC_ADD_ADDRESS);
            intent.putExtra("extra_address", address);
            AddAddressPresenter.this.addAddressView.context().sendBroadcast(intent);
            AddAddressPresenter.this.goBack();
        }
    }

}
