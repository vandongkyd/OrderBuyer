package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.SettingFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class SettingActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    private OrderComponent orderComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            SettingFragment settingFragment = new SettingFragment();
            addFragment(R.id.fragmentContainer, settingFragment);
            registerReceiver(mBroadcastReceiver, new IntentFilter(BC_ADD_ADDRESS));
            setBroadcastAction(new BroadcastAction() {
                @Override
                public void onAddAddress(Address address) {
                    settingFragment.onAddressAdded(address);
                }

                @Override
                public void onSubmitOrderSuccess() {

                }
            });
        }
    }

    private void initializeInjector() {
        this.orderComponent = DaggerOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public OrderComponent getComponent() {
        return orderComponent;
    }

    /**
     * Goes to the order list screen.
     */
    public void navigateToOrderList() {
        finish();
    }

    /**
     * Goes to the add address screen.
     */
    public void navigateToAddAddress() {
        navigator.navigateToAddAddress(this);
    }
}
