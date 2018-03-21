package com.fernandocejas.android10.order.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.presentation.internal.di.components.DaggerOrderComponent;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.fragment.OrderListFragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 *
 */

public class OrderListActivity extends BaseActivity implements HasComponent<OrderComponent>,
        NavigationView.OnNavigationItemSelectedListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, OrderListActivity.class);
    }

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.nav_view)
    NavigationView nav_view;

    private OrderComponent orderComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout_with_nav);

        ButterKnife.bind(this);

        this.initializeInjector();

        setupToolbar();
        setupNavigationDrawer();
        if (savedInstanceState == null) {
            OrderListFragment orderListFragment = new OrderListFragment();
            addFragment(R.id.fragmentContainer, orderListFragment);
            setBroadcastAction(new BroadcastAction() {
                @Override
                public void onAddAddress(Address address) {

                }

                @Override
                public void onSubmitOrderSuccess() {
                    orderListFragment.loadOrderList();
                }
            });
        }
        registerReceiver(mBroadcastReceiver, new IntentFilter(BC_SUBMIT_ORDER_SUCCESS));
    }

    @Override
    protected void onResume() {
        super.onResume();
        showContentInHeader();
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


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_button);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        }
    }

    private void setupNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(this);
    }

    private void showContentInHeader() {
        if (nav_view != null) {
            try {
                View header = nav_view.getHeaderView(0);
                showAvatarInView(header);
                showUserNameInView(header);
            } catch (Exception ex) {
            }
        }
    }

    private void showAvatarInView(View header) {
        ImageView imv_avatar = header.findViewById(R.id.imv_avatar);
        loadImageFromUrl(this, imv_avatar, Constants.USER_AVATAR, true, true);
        //action events
        imv_avatar.setOnClickListener(v -> {
            OrderListActivity.this.navigateToAccountInformation();
            drawer_layout.closeDrawer(GravityCompat.START);
        });
    }

    private void showUserNameInView(View header) {
        TextView tv_user_name = header.findViewById(R.id.tv_user_name);
        tv_user_name.setText(Constants.USER_NAME);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_payment:
                navigateToPayment();
                break;
            case R.id.action_settings:
                navigateToSetting();
                break;
            case R.id.action_logout:
                signOut();
                break;
            default:
                break;
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*switch (id) {
            case R.id.action_search:

                return true;
            default:
                break;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void loadImageFromUrl(Context context, ImageView view, String url, boolean isCircle, boolean hasDefault) {
        if (url == null || url.isEmpty()) {
            if (hasDefault) {
                //show default avatar if we don't have url to show
                Glide.with(context)
                        .load(R.drawable.default_avatar)
                        .asBitmap()
                        .into(new BitmapImageViewTarget(view) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                view.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
            return;
        }
        if (isCircle) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(new BitmapImageViewTarget(view) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            view.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } else {
            Glide.with(context)
                    .load(url)
                    .into(view);
        }
    }

    /**
     * Goes to the signIn screen.
     */
    public void navigateToSignIn() {
        this.navigator.navigateToSignIn(this, false);
        finish();
    }

    private void signOut() {
        PreferencesUtility.getInstance(this).writeString(PreferencesUtility.PREF_TOKEN, null);
        navigateToSignIn();
    }

    /**
     * Goes to the top website screen.
     */
    public void navigateToTopWebsite(String url) {
        this.navigator.navigateToTopWebsite(this, url);
    }

    /**
     * Goes to the order detail screen.
     */
    public void navigateToOrderDetail(String id, String status) {
        this.navigator.navigateToOrderDetail(this, id, status);
    }

    /**
     * Goes to the payment screen.
     */
    public void navigateToPayment() {
        this.navigator.navigateToPayment(this);
    }

    /**
     * Goes to the setting screen.
     */
    public void navigateToSetting() {
        this.navigator.navigateToSetting(this);
    }

    /**
     * Goes to the setting screen.
     */
    public void navigateToAccountInformation() {
        this.navigator.navigateToAccountInformation(this);
    }


}
