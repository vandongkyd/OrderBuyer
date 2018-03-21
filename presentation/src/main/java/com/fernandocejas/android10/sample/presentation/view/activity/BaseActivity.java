package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.presentation.utils.IMMLeaks;
import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String BC_EXIT = "broadcast_exit";
    public static final String BC_ADD_ADDRESS = "broadcast_add_address";
    public static final String BC_SUBMIT_ORDER_SUCCESS = "broadcast_submit_order_success";
    public static final String BC_ERROR_401 = "broadcast_error_401";

    @Inject
    protected Navigator navigator;

    protected BroadcastReceiver mBroadcastReceiver;
    protected BroadcastAction mBroadcastAction;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IMMLeaks.fixFocusedViewLeak(getApplication());
        this.getApplicationComponent().inject(this);
        registerBroadcastReceiver();
        registerReceiver(mBroadcastReceiver, new IntentFilter(BC_EXIT));
        registerReceiver(mBroadcastReceiver, new IntentFilter(BC_ERROR_401));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mBroadcastReceiver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        handleBackPressed();
    }

    public void handleBackPressed() {
        if (!BaseFragment.handleBackPressed(getSupportFragmentManager())) {
            super.onBackPressed();
        }
    }

    protected void registerBroadcastReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(BC_EXIT)) {
                    finish();
                } else if (action.equals(BC_ERROR_401)) {
                    finish();
                    BaseActivity.this.navigator.navigateToSignIn(BaseActivity.this, false);
                } else if (action.equals(BC_SUBMIT_ORDER_SUCCESS)) {
                    if (BaseActivity.this.mBroadcastAction != null) {
                        BaseActivity.this.mBroadcastAction.onSubmitOrderSuccess();
                    } else {
                        finish();
                    }
                } else if (action.equals(BC_ADD_ADDRESS)) {
                    Address address = (Address) intent.getSerializableExtra("extra_address");
                    if (BaseActivity.this.mBroadcastAction != null) {
                        BaseActivity.this.mBroadcastAction.onAddAddress(address);
                    }
                }
            }
        };
    }

//    /**
//     * Adds a {@link Fragment} to this activity's layout.
//     *
//     * @param containerViewId The container view to where add the fragment.
//     * @param fragment        The fragment to be added.
//     */
//    protected void addFragment(int containerViewId, Fragment fragment) {
//        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
//        fragmentTransaction.add(containerViewId, fragment);
//        fragmentTransaction.commit();
//    }

    public void addFragment(int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }

    public void addFragment(int containerViewId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            ft.replace(containerViewId, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            ft.replace(containerViewId, fragment).commit();
        }
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent}
     */
    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected interface BroadcastAction {
        void onAddAddress(Address address);

        void onSubmitOrderSuccess();
    }

    public void setBroadcastAction(BroadcastAction broadcastAction) {
        this.mBroadcastAction = broadcastAction;
    }
}
