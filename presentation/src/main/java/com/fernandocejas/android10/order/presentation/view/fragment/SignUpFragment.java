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
import android.widget.EditText;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.presenter.SignUpPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.SignUpView;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
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
public class SignUpFragment extends BaseFragment implements SignUpView {

    @Inject
    SignUpPresenter signUpPresenter;

    @Pattern(regex = "((\\b[a-zA-Z]{1,40}\\b)\\s*){2,}", messageResId = R.string.sign_up_name_error)
    @Bind(R.id.edt_name)
    EditText edt_name;

    @Email
    @Bind(R.id.edt_email)
    EditText edt_email;

    /*@Pattern(regex = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", messageResId = R.string.sign_up_phone_error)
    @Bind(R.id.edt_phone)
    EditText edt_phone;*/

    @Password
    @Bind(R.id.edt_password)
    EditText edt_password;

    @ConfirmPassword
    @Bind(R.id.edt_password_confirm)
    EditText edt_password_confirm;

    private ProgressDialog progressDialog;

    public SignUpFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.signUpPresenter.setView(this);
        if (savedInstanceState == null) {
            this.signUpPresenter.initialize(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.signUpPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.signUpPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.signUpPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.signUpPresenter = null;
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
    @OnClick(R.id.btn_sign_up)
    public void onSignUpClicked() {
        signUpPresenter.validateInputs();
    }

    @Override
    public void signUp() {
        signUpPresenter.callSignUp(
                edt_name.getText().toString(),
                edt_email.getText().toString(),
                edt_password.getText().toString());
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
    @OnClick(R.id.btn_sign_in)
    public void onSignInClicked() {
        signUpPresenter.navigationSignIn();
    }

    @Override
    @OnClick(R.id.lyt_name)
    public void onLayoutNameClicked() {
        edt_name.requestFocus();
    }

    @Override
    @OnClick(R.id.lyt_email)
    public void onLayoutEmailClicked() {
        edt_email.requestFocus();
    }

    @Override
    @OnClick(R.id.lyt_password)
    public void onLayoutPasswordClicked() {
        edt_password.requestFocus();
    }

    @Override
    @OnClick(R.id.lyt_password_confirm)
    public void onLayoutPasswordConfirmClicked() {
        edt_password_confirm.requestFocus();
    }
}
