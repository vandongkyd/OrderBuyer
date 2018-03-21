/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.fernandocejas.android10.BuildConfig;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.presenter.AddAddressPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.AddAddressView;
import com.fernandocejas.android10.order.presentation.view.adapter.PlaceAutocompleteAdapter;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class AddAddressFragment extends BaseFragment implements AddAddressView {

    private static final String TAG = "AddAddressFragment";

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    @Inject
    AddAddressPresenter addAddressPresenter;

    @Bind(R.id.edt_address)
    AutoCompleteTextView edt_address;

    /**
     * GeoDataClient wraps our service connection to Google Play services and provides access
     * to the Google Places API for Android.
     */
    protected GeoDataClient geoDataClient;

    private PlaceAutocompleteAdapter placeAutocompleteAdapter;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient fusedLocationProviderClient;

    private SupportMapFragment supportMapFragment;

    private ProgressDialog progressDialog;

    public AddAddressFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_add_address, container, false);
        ButterKnife.bind(this, fragmentView);
        setupMap();
        setupFusedLocationProviderClient();
        setupAutoComplete();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.addAddressPresenter.setView(this);
        if (savedInstanceState == null) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.addAddressPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.addAddressPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.addAddressPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.addAddressPresenter = null;
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            onLocationRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setupMap() {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (Utils.isGooglePlayServicesAvailable(activity())) {
            supportMapFragment.getMapAsync(googleMap -> {
                // check if map is created successfully or not
                if (googleMap == null) {
                    showError(getString(R.string.add_address_create_map_error));
                    return;
                }
                AddAddressFragment.this.addAddressPresenter.setGoogleMap(googleMap);
            });
        }
    }

    private void setupFusedLocationProviderClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity());

    }

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data Client
     * to retrieve more details about the place.
     *
     * @see GeoDataClient#getPlaceById(String...)
     */
    private AdapterView.OnItemClickListener onAutocompleteClickListener = (parent, view, position, id) -> {
        final AutocompletePrediction item = placeAutocompleteAdapter.getItem(position);
        AddAddressFragment.this.OnItemAddressClicked(item);
    };

    private void setupAutoComplete() {
        // Construct a GeoDataClient for the Google Places API for Android.
        geoDataClient = Places.getGeoDataClient(context(), null);
        // Register a listener that receives callbacks when a suggestion has been selected
        edt_address.setOnItemClickListener(onAutocompleteClickListener);
        // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(context(), geoDataClient, BOUNDS_GREATER_SYDNEY, null);
        edt_address.setAdapter(placeAutocompleteAdapter);
    }

    @Override
    public void showLocationMarkerInView(GoogleMap googleMap, LatLng location) {
        // Add a marker in 'location',
        // and move the map's camera to the same location.
        googleMap.addMarker(new MarkerOptions().position(location));
    }

    @Override
    public void showAddressInView(String address) {
        // https://stackoverflow.com/questions/5495225/how-to-disable-autocompletetextviews-drop-down-from-showing-up
        edt_address.setThreshold(Integer.MAX_VALUE);
        edt_address.setText(address);
        edt_address.setThreshold(1);
    }

    @Override
    public void navigationLocationInView(GoogleMap googleMap, LatLng latLng) {
        float fZoom = 15.0f;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, fZoom));
    }

    @Override
    public void loadLastLocation() {
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    @Override
    public boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(activity(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermissions() {
        boolean shouldProvideRationale =
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional activity.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    view -> {
                        // Request permission
                        startLocationPermissionRequest();
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void getLastLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful() && task.getResult() != null) {

                        Location location = task.getResult();
                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            AddAddressFragment.this.navigationLocationInView(AddAddressFragment.this.addAddressPresenter.getGoogleMap(), latLng);
                        }

                    } else {
                        Log.w(TAG, "getLastLocation:exception", task.getException());
                        AddAddressFragment.this.showError(AddAddressFragment.this.getContext().getString(R.string.no_location_detected));
                    }
                });
    }


    @Override
    public void startLocationPermissionRequest() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onLocationRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length <= 0) {
            // If user interaction was interrupted, the permission request is cancelled and you
            // receive empty arrays.
            Log.i(TAG, "User interaction was cancelled.");
        } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted.
            getLastLocation();
        } else {
            // Permission denied.

            // Notify the user via a SnackBar that they have rejected a core permission for the
            // app, which makes the Activity useless. In a real app, core permissions would
            // typically be best requested during a welcome-screen flow.

            // Additionally, it is important to remember that a permission might have been
            // rejected without asking the user for permission (device policy or "Never ask
            // again" prompts). Therefore, a user interface affordance is typically implemented
            // when permissions are denied. Otherwise, your app could appear unresponsive to
            // touches or interactions which have required permissions.
            showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                    view -> {
                        // Build intent that displays the App settings screen.
                        Intent intent = new Intent();
                        intent.setAction(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",
                                BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    });
        }
    }

    @Override
    public void showSnackbar(int mainTextStringId, int actionStringId, View.OnClickListener listener) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Callback for results from a Places Geo Data Client query that shows the first place result in
     * the details view on screen.
     */
    private OnCompleteListener<PlaceBufferResponse> onUpdatePlaceDetailsCallback
            = task -> {
        try {
            PlaceBufferResponse places = task.getResult();

            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            LatLng latLng = place.getLatLng();
            if (latLng != null) {
                navigationLocationInView(
                        AddAddressFragment.this.addAddressPresenter.getGoogleMap(),
                        latLng);
                // hide virtual keyboard
                InputMethodManager imm = (InputMethodManager) activity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_address.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        } catch (RuntimeRemoteException e) {
            // Request did not complete successfully
            Log.e(TAG, "Place query did not complete.", e);
            return;
        }
    };

    @Override
    public void OnItemAddressClicked(AutocompletePrediction item) {
        /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
        final String placeId = item.getPlaceId();
        final CharSequence primaryText = item.getPrimaryText(null);

        Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data Client to retrieve a Place object with
             additional details about the place.
              */
        Task<PlaceBufferResponse> placeResult = geoDataClient.getPlaceById(placeId);
        placeResult.addOnCompleteListener(onUpdatePlaceDetailsCallback);

        Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
    }

    @Override
    @OnClick(R.id.imv_clear)
    public void onClearOnSearchClick() {
        edt_address.setText(null);
        edt_address.requestFocus();
        // show virtual keyboard
        new Handler().post(mShowImeRunnable);
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.addAddressPresenter.goBack();
    }

    @Override
    @OnClick(R.id.btn_done)
    public void onButtonDoneClicked() {
        this.addAddressPresenter.submitAddress();
    }

    private Runnable mShowImeRunnable = () -> {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.showSoftInput(edt_address, 0);
        }
    };
}
