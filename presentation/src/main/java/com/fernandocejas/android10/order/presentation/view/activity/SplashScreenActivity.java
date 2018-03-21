package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.SplashScreenFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class SplashScreenActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SplashScreenActivity.class);
    }

    private OrderComponent orderComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new SplashScreenFragment());
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
     * Goes to the signIn screen.
     */
    public void navigateToSignIn() {
        this.navigator.navigateToSignIn(this, false);
        finish();
    }

    /**
     * Goes to the order list screen.
     */
    public void navigateToOrderList() {
        this.navigator.navigateToOrderList(this);
        finish();
    }
}
