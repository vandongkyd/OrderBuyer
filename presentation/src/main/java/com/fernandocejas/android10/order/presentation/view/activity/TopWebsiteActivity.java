package com.fernandocejas.android10.order.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.TopWebsiteFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class TopWebsiteActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context, String url) {
        Intent intent = new Intent(context, TopWebsiteActivity.class);
        intent.putExtra("extra_url", url);
        return intent;
    }

    public static Intent getCallingIntentForResult(Context context, String url, int request_code) {
        Intent intent = new Intent(context, TopWebsiteActivity.class);
        intent.putExtra("extra_url", url);
        intent.putExtra("extra_request_code", request_code);
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
            TopWebsiteFragment topWebsiteFragment = new TopWebsiteFragment();
            String url;
            int request_code;
            url = getIntent().getStringExtra("extra_url");
            request_code = getIntent().getIntExtra("extra_request_code", -1);
            Bundle arguments = new Bundle();
            arguments.putString("args_url", url);
            arguments.putInt("args_request_code", request_code);
            topWebsiteFragment.setArguments(arguments);
            addFragment(R.id.fragmentContainer, topWebsiteFragment);
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
     * Goes to the product screen.
     */
    public void navigateToProduct(Product product, int requestCode) {
        if (requestCode == -1) {
            this.navigator.navigateToProduct(this, product);
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("return_product", product);
            setResult(Activity.RESULT_OK, returnIntent);
        }
        finish();
    }
}
