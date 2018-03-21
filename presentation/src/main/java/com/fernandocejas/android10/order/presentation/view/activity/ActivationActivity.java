package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.ActivationFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class ActivationActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context, String token, String phone, boolean is_recall) {
        Intent intent = new Intent(context, ActivationActivity.class);
        intent.putExtra("extra_token", token);
        intent.putExtra("extra_phone", phone);
        intent.putExtra("extra_is_recall", is_recall);
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
            ActivationFragment activationFragment = new ActivationFragment();
            String token, phone;
            token = getIntent().getStringExtra("extra_token");
            phone = getIntent().getStringExtra("extra_phone");
            boolean is_recall = getIntent().getBooleanExtra("extra_is_recall", false);
            Bundle arguments = new Bundle();
            arguments.putString("args_token", token);
            arguments.putString("args_phone", phone);
            arguments.putBoolean("args_is_recall", is_recall);
            activationFragment.setArguments(arguments);
            addFragment(R.id.fragmentContainer, activationFragment);
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
        this.sendBroadcast(new Intent(BaseActivity.BC_EXIT));
        this.navigator.navigateToOrderList(this);

    }
}
