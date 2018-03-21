/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.presenter.SignInPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.SignInView;
import com.fernandocejas.android10.order.presentation.view.activity.SignUpActivity;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class SignInFragment extends BaseFragment implements SignInView, Validator.ValidationListener {

    @Inject
    SignInPresenter signInPresenter;

    @Email
    @Bind(R.id.edt_email)
    EditText edt_email;

    @Password
    @Bind(R.id.edt_password)
    EditText edt_password;

    private Validator validator;

    private ProgressDialog progressDialog;

    public SignInFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, fragmentView);
        setupValidator();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.signInPresenter.setView(this);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            boolean is_recall = arguments.getBoolean("args_is_recall");
            this.signInPresenter.setIs_recall(is_recall);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.signInPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.signInPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.signInPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.signInPresenter = null;
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
    public void onButtonSignUpClick() {
        this.signInPresenter.navigateToSignUp();
    }

    @Override
    @OnClick(R.id.btn_sign_in)
    public void onButtonSignInClick() {
        validator.validate();
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
    public void onValidationSucceeded() {
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        signInPresenter.callSignIn(email, password);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            EditText editText = (EditText) error.getView();
            editText.setError(error.getCollatedErrorMessage(context()));
        }
    }

    private void setupValidator(){
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

}