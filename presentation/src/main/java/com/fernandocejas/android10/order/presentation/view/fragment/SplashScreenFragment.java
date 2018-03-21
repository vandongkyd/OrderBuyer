/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.presenter.SplashScreenPresenter;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.SplashScreenView;
import com.fernandocejas.android10.order.presentation.view.dialog.NetworkErrorDialog;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 *
 *
 */
public class SplashScreenFragment extends BaseFragment implements SplashScreenView {

    @Inject
    SplashScreenPresenter splashScreenPresenter;

    private ProgressDialog progressDialog;
    private NetworkErrorDialog mNetworkErrorDialog;
    private Handler h = null;
    private Runnable r = null;

    public SplashScreenFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.splashScreenPresenter.setView(this);
        if (savedInstanceState == null) {
            h = new Handler();
            if (r == null) {
                r = () -> {
                    initialize();
                };
            }
            String access_token = PreferencesUtility.getInstance(context())
                    .readString(PreferencesUtility.PREF_TOKEN, null);
            if (access_token == null || access_token.isEmpty()) {
                h.postDelayed(r, TimeUnit.SECONDS.toMillis(1));
            } else {
                initialize();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.splashScreenPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.splashScreenPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.splashScreenPresenter.destroy();
        this.h.removeCallbacks(r);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.splashScreenPresenter = null;
        this.h = null;
    }

    @Override
    public void showNetWorkError() {
        if (mNetworkErrorDialog == null) {
            mNetworkErrorDialog = new NetworkErrorDialog(activity(), new NetworkErrorDialog.OnClickListener() {
                @Override
                public void onRetryClicked() {
                    SplashScreenFragment.this.onRetryClicked();
                }

                @Override
                public void onDismissClicked() {
                    SplashScreenFragment.this.onDismissClicked();
                }
            });
        }
        if (!mNetworkErrorDialog.isShowing()) {
            mNetworkErrorDialog.show();
        }
    }

    @Override
    public void dismissNetWorkError() {
        if (mNetworkErrorDialog != null) {
            mNetworkErrorDialog.dismiss();
        }
    }

    @Override
    public void onRetryClicked() {
        initialize();
    }

    @Override
    public void onDismissClicked() {
        getActivity().finish();
    }

    @Override
    public void showLoading() {
        /*if (progressDialog == null) {
            progressDialog = DialogFactory.createProgressDialog(activity(), R.string.processing);
        }
        progressDialog.show();*/
    }

    @Override
    public void hideLoading() {
        /*if (progressDialog != null) {
            progressDialog.dismiss();
        }*/
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    /**
     * Check network connection
     */
    public void initialize() {
        this.splashScreenPresenter.initialize();
    }

}
