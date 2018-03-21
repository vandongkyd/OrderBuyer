package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.OrderDetailAcceptedFragment;
import com.fernandocejas.android10.order.presentation.view.fragment.OrderDetailFragment;
import com.fernandocejas.android10.order.presentation.view.fragment.ProductDetailFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class OrderDetailActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context, String id, String status) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("extra_id", id);
        intent.putExtra("extra_status", status);
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
            String order_id = getIntent().getStringExtra("extra_id");
            String order_status = getIntent().getStringExtra("extra_status");
            //
            if (order_status.equals("0")) {//trạng thái tạo mới
                OrderDetailFragment orderFragment = new OrderDetailFragment();
                Bundle arguments = new Bundle();
                arguments.putSerializable("args_id", order_id);
                orderFragment.setArguments(arguments);
                addFragment(R.id.fragmentContainer, orderFragment);
            } else {
                OrderDetailAcceptedFragment orderDetailAcceptedFragment = new OrderDetailAcceptedFragment();
                Bundle arguments = new Bundle();
                arguments.putSerializable("args_id", order_id);
                orderDetailAcceptedFragment.setArguments(arguments);
                addFragment(R.id.fragmentContainer, orderDetailAcceptedFragment);
            }

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

    public void navigateToProductDetail(Product product) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("args_product", product);
        productDetailFragment.setArguments(arguments);
        addFragment(R.id.fragmentContainer, productDetailFragment, true);
    }

    public void navigateToAcceptedOffer(Offer offer,
                                        String quantity,
                                        String amount,
                                        String weight,
                                        String sale_tax,
                                        String service_fee,
                                        String currency) {
        this.navigator.navigateToAccepted(this, offer, quantity, amount, weight, sale_tax, service_fee, currency);

    }

    public void navigateToChat(String order_id,
                               String provider_id,
                               String user_id,
                               String provider_avatar) {
        this.navigator.navigateToChat(this, order_id, provider_id, user_id, provider_avatar);
    }

}
