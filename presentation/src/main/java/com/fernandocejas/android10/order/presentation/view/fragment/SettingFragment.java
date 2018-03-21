/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.presenter.SettingPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.SettingView;
import com.fernandocejas.android10.order.presentation.view.adapter.AddressAdapter;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class SettingFragment extends BaseFragment implements SettingView {

    @Inject
    SettingPresenter settingPresenter;
    @Inject
    AddressAdapter addressAdapter;

    @Bind(R.id.imv_avatar)
    ImageView imv_avatar;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.tv_email)
    TextView tv_email;
    @Bind(R.id.rv_addresses)
    RecyclerView rv_addresses;

    private ProgressDialog progressDialog;

    public SettingFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(OrderComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecycleView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.settingPresenter.setView(this);
        if (savedInstanceState == null) {
            this.settingPresenter.loadUserInfo();
            this.settingPresenter.loadAddress();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.settingPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.settingPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.settingPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.settingPresenter = null;
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.settingPresenter.goBack();
    }

    @Override
    public void showAvatarInView(String url) {
        this.loadImageFromUrl(activity(), imv_avatar, url, true, true);
    }

    @Override
    public void showNameInView(String name) {
        tv_name.setText(name);
    }

    @Override
    public void showPhoneInView(String phone) {
        tv_phone.setText(phone);
    }

    @Override
    public void showEmailInView(String email) {
        tv_email.setText(email);
    }

    @Override
    public void renderAddresses(Collection<AddressModel> addressModels) {
        this.addressAdapter.setAddressModels(addressModels);
    }

    @Override
    @OnClick(R.id.btn_add_address)
    public void onAddAddressClicked() {
        this.settingPresenter.goToAddAddress();
    }

    @Override
    public void onItemAddressClicked(AddressModel addressModel) {

    }

    @Override
    public void onAddressAdded(Address address) {
        this.settingPresenter.addAddress(address);
    }

    @Override
    public void showLoading() {
        if (this.progressDialog == null) {
            this.progressDialog = DialogFactory.createProgressDialog(activity(), R.string.processing);
        }
        this.progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(activity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
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

    private AddressAdapter.OnItemClickListener onItemClickListener = addressModel -> {
        SettingFragment.this.onItemAddressClicked(addressModel);
    };

    private void setupRecycleView() {
        this.addressAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_addresses.setLayoutManager(new LinearLayoutManager(context()));
        this.rv_addresses.setAdapter(addressAdapter);
    }
}
