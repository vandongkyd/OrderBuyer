/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.fernandocejas.android10.sample.presentation.view.LoadDataView;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface AddAddressView extends LoadDataView {

    void showLocationMarkerInView(GoogleMap googleMap, LatLng location);

    void showAddressInView(String address);

    void navigationLocationInView(GoogleMap googleMap, LatLng location);

    void loadLastLocation();

    /**
     * Return the current state of the permissions needed.
     */
    boolean checkPermissions();

    void requestPermissions();

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    void getLastLocation();

    void startLocationPermissionRequest();

    void onLocationRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                            @NonNull int[] grantResults);

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    void showSnackbar(final int mainTextStringId, final int actionStringId,
                      View.OnClickListener listener);

    void OnItemAddressClicked(AutocompletePrediction item);

    void onClearOnSearchClick();

    void onButtonDoneClicked();

    void onBackClicked();

}

