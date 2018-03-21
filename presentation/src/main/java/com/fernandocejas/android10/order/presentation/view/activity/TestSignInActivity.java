package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.Fragment_SignIn_Test;
import com.fernandocejas.android10.order.presentation.view.fragment.SignInFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

/**
 * Created by vandongluong on 3/6/18.
 */

public class TestSignInActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context, boolean is_recall) {
        Intent intent = new Intent(context, TestSignInActivity.class);
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
            boolean is_recall = getIntent().getBooleanExtra("extra_is_recall", false);
            //
            Fragment_SignIn_Test signInFragment = new Fragment_SignIn_Test();
            Bundle arguments = new Bundle();
            signInFragment.setArguments(arguments);
            arguments.putBoolean("args_is_recall", is_recall);
            addFragment(R.id.fragmentContainer, signInFragment);
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
    public void navigateToActivation(String token, String phone, boolean is_recall) {
        this.navigator.navigateToActivation(this, token, phone, is_recall);
        finish();
    }
    public void navigateToOrderList() {
        this.navigator.navigateToOrderList(this);
        finish();
    }
}
