/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.CountryModel;
import com.fernandocejas.android10.order.presentation.presenter.PhoneCodeListPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.PhoneCodeListView;
import com.fernandocejas.android10.order.presentation.view.adapter.CountryAdapter;
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
public class PhoneCodeListFragment extends BaseFragment implements PhoneCodeListView {

    @Inject
    PhoneCodeListPresenter phoneCodeListPresenter;
    @Inject
    CountryAdapter countryAdapter;

    @Bind(R.id.rv_phone)
    RecyclerView rv_phone;

    private ProgressDialog progressDialog;

    public PhoneCodeListFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_phone_code_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.phoneCodeListPresenter.setView(this);
        if (savedInstanceState == null) {
            loadCountryList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.phoneCodeListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.phoneCodeListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.phoneCodeListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.phoneCodeListPresenter = null;
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = DialogFactory.createProgressDialog(activity(), R.string.processing);
        }
        progressDialog.show();
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

    private void setupRecyclerView() {
        this.countryAdapter.setOnItemClickListener(countryModel -> {
            this.phoneCodeListPresenter.onCountryClicked(countryModel);
        });
        this.rv_phone.setLayoutManager(new LinearLayoutManager(context()));
        this.rv_phone.setAdapter(this.countryAdapter);
    }

    private void loadCountryList() {
        this.phoneCodeListPresenter.initialize();
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.phoneCodeListPresenter.navigationVerifyPhone();
    }

    @Override
    public void renderPhoneCodeList(Collection<CountryModel> countryModelCollection) {
        if (countryModelCollection != null) {
            this.countryAdapter.setCountriesCollection(countryModelCollection);
        }
    }
}
