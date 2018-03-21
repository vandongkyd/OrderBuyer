package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.domain.SettingByCountry;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.ProductListFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class ProductListActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context, Product product) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra("extra_product", product);
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
            Product product = (Product) getIntent().getSerializableExtra("extra_product");
            //
            ProductListFragment productFragment = new ProductListFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable("args_product", product);
            productFragment.setArguments(arguments);
            addFragment(R.id.fragmentContainer, productFragment);
        }
        registerReceiver(mBroadcastReceiver, new IntentFilter(BC_SUBMIT_ORDER_SUCCESS));
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

    public void navigateToTopWebsite(String url) {
        if (url != null) {
            this.navigator.navigateToTopWebsite(this, url);
            finish();
        }
    }

    public void navigateToTopWebsiteForResult(String url, Fragment fragment, int requestCode) {
        if (url != null) {
            this.navigator.navigateToTopWebsiteForResult(this, url, fragment, requestCode);
        }
    }

    public void navigateToOrder(SettingByCountry settingByCountry) {
        this.navigator.navigateToOrder(this, settingByCountry);
    }

    public void navigateToOrderList() {
        finish();
    }

}
