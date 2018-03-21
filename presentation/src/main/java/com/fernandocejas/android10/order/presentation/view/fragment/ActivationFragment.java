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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.presenter.ActivationPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.ActivationView;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class ActivationFragment extends BaseFragment implements ActivationView {

    @Inject
    ActivationPresenter activationPresenter;

    @Bind(R.id.tv_sent_to)
    TextView tv_sent_to;
    @Bind({R.id.tv_code_1, R.id.tv_code_2, R.id.tv_code_3, R.id.tv_code_4})
    TextView[] tv_code;

    private ProgressDialog progressDialog;
    private String token;
    private String phone;

    public ActivationFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_activation, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.activationPresenter.setView(this);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            token = arguments.getString("args_token");
            phone = arguments.getString("args_phone");
            //
            this.activationPresenter.setIs_recall(arguments.getBoolean("args_is_recall"));
            //show phone in text view
            tv_sent_to.setText(context().getString(R.string.activation_sent_to, phone));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.activationPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.activationPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.activationPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activationPresenter = null;
    }


    @Override
    @OnClick({R.id.tv_0, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9})
    public void onActivationButtonClicked(View view) {
        String codeText = ((TextView) view).getText().toString();
        this.setCodeToView(codeText);
    }

    @Override
    @OnClick(R.id.imv_delete)
    public void onDeleteButtonClicked() {
        for (int i = tv_code.length - 1; i >= 0; i--) {
            String text = tv_code[i].getText().toString();
            if (text != null && !text.isEmpty()) {
                tv_code[i].setText(null);
                break;
            }
        }
    }

    @Override
    public void setCodeToView(String codeText) {
        for (int i = 0; i < tv_code.length; i++) {
            String text = tv_code[i].getText().toString();
            if (text == null || text.isEmpty()) {
                tv_code[i].setText(codeText);
                if (i >= tv_code.length - 1) {
                    this.activateCode();
                }
                break;
            }
        }
    }

    @Override
    public void activateCode() {
        String code = "";
        for (int i = 0; i < tv_code.length; i++) {
            code += tv_code[i].getText().toString();
        }
        activationPresenter.callActivateCode(token, code);
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
        Toast.makeText(context(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        getActivity().finish();
    }

    @Override
    @OnClick(R.id.tv_resend_activation_code)
    public void onResendActiveCodeClicked() {
        this.activationPresenter.callResendActiveCode(token);
    }
}
