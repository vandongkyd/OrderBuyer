package com.fernandocejas.android10.order.presentation.view.activity;

import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.ContactFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 * Created by vandongluong on 3/15/18.
 */

public class ContactActivity extends BaseActivity implements HasComponent<OrderComponent> {
    private OrderComponent orderComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            setContentView(R.layout.activity_layout);

            this.initializeInjector();
            if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new ContactFragment());
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
}
