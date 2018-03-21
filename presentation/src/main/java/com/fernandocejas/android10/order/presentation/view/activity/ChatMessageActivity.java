package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.view.fragment.ChatMessageFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

/**
 *
 *
 */

public class ChatMessageActivity extends BaseActivity implements HasComponent<OrderComponent> {

    public static Intent getCallingIntent(Context context,
                                          String order_id,
                                          String provider_id,
                                          String user_id,
                                          String provider_avatar) {
        Intent intent = new Intent(context, ChatMessageActivity.class);
        intent.putExtra("extra_order_id", order_id);
        intent.putExtra("extra_provider_id", provider_id);
        intent.putExtra("extra_user_id", user_id);
        intent.putExtra("extra_provider_avatar", provider_avatar);
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
            String order_id = getIntent().getStringExtra("extra_order_id");
            String provider_id = getIntent().getStringExtra("extra_provider_id");
            String user_id = getIntent().getStringExtra("extra_user_id");
            String provider_avatar = getIntent().getStringExtra("extra_provider_avatar");
            //
            ChatMessageFragment chatMessageFragment = new ChatMessageFragment();
            Bundle arguments = new Bundle();
            arguments.putString("args_order_id", order_id);
            arguments.putString("args_provider_id", provider_id);
            arguments.putString("args_user_id", user_id);
            arguments.putString("args_provider_avatar", provider_avatar);
            chatMessageFragment.setArguments(arguments);
            addFragment(R.id.fragmentContainer, chatMessageFragment);
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

    public void navigateToOrderDetail() {
        finish();
    }

}
