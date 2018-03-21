package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.AcceptedFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class AcceptedActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context, Offer offer,
                                          String quantity,
                                          String amount,
                                          String weight,
                                          String sale_tax,
                                          String service_fee,
                                          String currency) {
        Intent intent = new Intent(context, AcceptedActivity.class);
        intent.putExtra("extra_offer", offer);
        intent.putExtra("extra_quantity", quantity);
        intent.putExtra("extra_amount", amount);
        intent.putExtra("extra_weight", weight);
        intent.putExtra("extra_sale_tax", sale_tax);
        intent.putExtra("extra_service_fee", service_fee);
        intent.putExtra("extra_currency", currency);
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
            Intent intent = getIntent();
            Offer offer = (Offer) intent.getSerializableExtra("extra_offer");
            String quantity = intent.getStringExtra("extra_quantity");
            String amount = intent.getStringExtra("extra_amount");
            String weight = intent.getStringExtra("extra_weight");
            String sale_tax = intent.getStringExtra("extra_sale_tax");
            String service_fee = intent.getStringExtra("extra_service_fee");
            String currency = intent.getStringExtra("extra_currency");
            //
            AcceptedFragment acceptedFragment = new AcceptedFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable("args_offer", offer);
            arguments.putString("args_quantity", quantity);
            arguments.putString("args_amount", amount);
            arguments.putString("args_weight", weight);
            arguments.putString("args_sale_tax", sale_tax);
            arguments.putString("args_service_fee", service_fee);
            arguments.putString("args_currency", currency);
            acceptedFragment.setArguments(arguments);
            addFragment(R.id.fragmentContainer, acceptedFragment);
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
     * Goes to the order detail screen.
     */
    public void navigateToOrderDetail() {
        finish();
    }
}
