package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.PhoneCodeListFragment;
import com.fernandocejas.android10.order.presentation.view.fragment.SignUpFragment;
import com.fernandocejas.android10.order.presentation.view.fragment.VerifyPhoneFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class SignUpActivity extends BaseActivity implements HasComponent<OrderComponent> {

    private String phoneCode;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    private OrderComponent orderComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new SignUpFragment());
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

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = "+" + phoneCode;
    }

    /**
     * Goes to the signIn screen.
     */
    public void navigateToSignIn() {
        finish();
    }

    /**
     * Goes to the verify phone screen.
     */
    public void navigateVerifyPhone(String name, String email, String password) {
        VerifyPhoneFragment verifyPhoneFragment = new VerifyPhoneFragment();
        Bundle arguments = new Bundle();
        arguments.putString("args_name", name);
        arguments.putString("args_email", email);
        arguments.putString("args_password", password);
        verifyPhoneFragment.setArguments(arguments);
        addFragment(R.id.fragmentContainer, verifyPhoneFragment, true);
    }

    /**
     * Goes to phone code list screen.
     */
    public void navigateToPhoneCodeList() {
        addFragment(R.id.fragmentContainer, new PhoneCodeListFragment(), true);
    }

    /**
     * Goes to activation screen.
     */
    public void navigateToActivation(String token, String phone) {
        this.navigator.navigateToActivation(this, token, phone, false);
        finish();
    }
}
