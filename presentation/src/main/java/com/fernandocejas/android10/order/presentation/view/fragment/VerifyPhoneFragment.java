/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.presenter.VerifyPhonePresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.VerifyPhoneView;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class VerifyPhoneFragment extends BaseFragment implements VerifyPhoneView {

    @Inject
    VerifyPhonePresenter verifyPhonePresenter;

    @Bind(R.id.tv_phone_code)
    TextView tv_phone_code;
    @Bind(R.id.imv_country_flag)
    ImageView imv_country_flag;

    @Pattern(regex = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", messageResId = R.string.sign_up_phone_error)
    @Bind(R.id.edt_phone)
    EditText edt_phone;

    private ProgressDialog progressDialog;
    private String name, email, password;

    public VerifyPhoneFragment() {
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
        Bundle arguments = getArguments();
        name = arguments.getString("args_name");
        email = arguments.getString("args_email");
        password = arguments.getString("args_password");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_verify_phone, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.verifyPhonePresenter.setView(this);
        if (savedInstanceState == null) {
            this.verifyPhonePresenter.initialize(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.verifyPhonePresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.verifyPhonePresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.verifyPhonePresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.verifyPhonePresenter = null;
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

    @Override
    @OnClick({R.id.tv_0, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9})
    public void onActivationButtonClicked(View view) {
        String codeText = ((TextView) view).getText().toString();
        this.setCodeToView(codeText);
    }

    @Override
    @OnClick(R.id.imv_delete)
    public void onDeleteButtonClicked() {
        String phone = edt_phone.getText().toString();
        if (phone != null && phone.length() > 0) {
            phone = phone.substring(0, phone.length() - 1);
            edt_phone.setText(phone);
        }
    }

    @Override
    public void setCodeToView(String codeText) {
        String phone = edt_phone.getText().toString();
        if (phone != null && phone.length() < 12) {
            phone += codeText;
            edt_phone.setText(phone);
        }
    }

    @Override
    @OnClick(R.id.btn_send_verification_code)
    public void onSendVerificationCodeClicked() {
        verifyPhonePresenter.validateInputs();
    }

    @Override
    public void sendVerificationCode() {
        verifyPhonePresenter.callSignUp(
                name,
                email,
                edt_phone.getText().toString(),
                password,
                tv_phone_code.getText().toString());
    }

    @Override
    public void onValidateError(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            EditText editText = (EditText) error.getView();
            editText.setError(error.getCollatedErrorMessage(activity()));
        }
    }

    @Override
    public void resetValidate() {
        //nothing
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        verifyPhonePresenter.navigationSignIn();
    }

    @Override
    public void showPhoneCode(String phone_code) {
        String isoCode = Utils.getISOFromCountryZipCode(context(), phone_code.replace("+", ""));
        Glide.with(context())
                .load(Uri.parse("file:///android_asset/flag/" + isoCode.toLowerCase() + ".png"))
                .into(imv_country_flag);
        tv_phone_code.setText(phone_code);
    }

    @Override
    @OnClick(R.id.cmd_choice_phone)
    public void openPhoneCodeList() {
        verifyPhonePresenter.navigationPhoneCodeList();
    }
}
