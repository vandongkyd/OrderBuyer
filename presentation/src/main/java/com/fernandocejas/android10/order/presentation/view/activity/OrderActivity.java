package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.SettingByCountry;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.OrderFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class OrderActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context, SettingByCountry settingByCountry) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("extra_setting", settingByCountry);
        return intent;
    }

    private OrderComponent orderComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            SettingByCountry setting = (SettingByCountry) getIntent().getSerializableExtra("extra_setting");
            //
            OrderFragment orderFragment = new OrderFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable("args_setting", setting);
            orderFragment.setArguments(arguments);
            addFragment(R.id.fragmentContainer, orderFragment);
            registerReceiver(mBroadcastReceiver, new IntentFilter(BC_ADD_ADDRESS));
            setBroadcastAction(new BroadcastAction() {
                @Override
                public void onAddAddress(Address address) {
                    orderFragment.onAddressAdded(address);
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
     * Goes to the product screen.
     */
    public void navigateToProduct() {
        finish();
    }

    /**
     * Goes to the order list screen.
     */
    public void navigateToOrderList() {
        this.sendBroadcast(new Intent(BaseActivity.BC_SUBMIT_ORDER_SUCCESS));
        finish();
    }

    /**
     * Goes to the add address screen.
     */
    public void navigateToAddAddress() {
        navigator.navigateToAddAddress(this);
    }
}
