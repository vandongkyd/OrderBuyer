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
import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.presenter.PaymentListPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.PaymentListView;
import com.fernandocejas.android10.order.presentation.view.adapter.ItemTouchHelperCallback;
import com.fernandocejas.android10.order.presentation.view.adapter.PaymentAdapter;
import com.fernandocejas.android10.order.presentation.view.dialog.AddPaymentDialog;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class PaymentListFragment extends BaseFragment implements PaymentListView {

    @Inject
    PaymentListPresenter paymentListPresenter;
    @Inject
    PaymentAdapter paymentAdapter;

    @Bind(R.id.rv_payments)
    RecyclerView rv_payments;

    private ProgressDialog progressDialog;
    private AddPaymentDialog addPaymentDialog;

    private PaymentAdapter.OnItemClickListener onItemClickListener = new PaymentAdapter.OnItemClickListener() {
        @Override
        public void onItemClicked(PaymentModel paymentModel) {

        }

        @Override
        public void onItemRemoved(PaymentModel paymentModel, int position) {
            PaymentListFragment.this.onPaymentRemoveClicked(paymentModel, position);
        }
    };

    public PaymentListFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_payment_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecycleView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.paymentListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.paymentListPresenter.loadPaymentList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.paymentListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.paymentListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.paymentListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.paymentListPresenter = null;
    }

    @Override
    public void renderPaymentMethodList(Collection<PaymentModel> paymentModels) {
        this.paymentAdapter.setPaymentModels(paymentModels);
    }

    @Override
    @OnClick(R.id.btn_add_payment)
    public void onAddPaymentClicked() {
        if (this.addPaymentDialog == null) {
            this.addPaymentDialog = new AddPaymentDialog(activity());
            this.addPaymentDialog.setOnClickListener(onDialogClickListener);
        }
        this.addPaymentDialog.show();
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.paymentListPresenter.goBack();
    }

    @Override
    public void onSavePaymentInDialogClicked(String card_number, int exp_month, int exp_year, String cvc) {
        this.paymentListPresenter.createTokenFromStripe(card_number, exp_month, exp_year, cvc);
    }

    @Override
    public void onPaymentRemoveClicked(PaymentModel paymentModel, int position) {
        this.paymentListPresenter.deletePayment(paymentModel.getId() + "", position);
    }

    @Override
    public void showDeleteSuccess(int position) {
        this.paymentAdapter.onItemRemoved(position);
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

    private AddPaymentDialog.OnClickListener onDialogClickListener = new AddPaymentDialog.OnClickListener() {
        @Override
        public void onSaveClicked(String card_number, int exp_month, int exp_year, String cvc) {
            PaymentListFragment.this.onSavePaymentInDialogClicked(card_number, exp_month, exp_year, cvc);
        }

        @Override
        public void onDismissClicked() {

        }
    };

    private void setupRecycleView() {
        this.paymentAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_payments.setLayoutManager(new LinearLayoutManager(context()));
        this.rv_payments.setAdapter(paymentAdapter);
        this.initSwipe();
    }

    private void initSwipe() {
        ItemTouchHelperCallback callback = new ItemTouchHelperCallback();
        ItemTouchHelperExtension itemTouchHelper = new ItemTouchHelperExtension(callback);
        itemTouchHelper.attachToRecyclerView(rv_payments);
        paymentAdapter.setItemTouchHelperExtension(itemTouchHelper);
    }
}
