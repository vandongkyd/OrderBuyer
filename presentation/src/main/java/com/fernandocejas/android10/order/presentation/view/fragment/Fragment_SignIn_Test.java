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
 * Created by vandongluong on 3/6/18.
 */

public class Fragment_SignIn_Test extends BaseFragment implements SignInView, Validator.ValidationListener {

    @Inject
    SignInPresenter signInPresenter;

    @Email
    @Bind(R.id.edit_username_test)
    EditText edit_username_test;

    @Password
    @Bind(R.id.edit_password_test)
    EditText edit_password_test;


    private Validator validator;

    private ProgressDialog progressDialog;

    public Fragment_SignIn_Test() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_sign_in_test, container, false);
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
    public void onButtonSignUpClick() {

    }

    @Override
    @OnClick(R.id.bt_sigin_test)
    public void onButtonSignInClick() {
        validator.validate();
    }

    @Override
    public void onLayoutEmailClicked() {

    }

    @Override
    public void onLayoutPasswordClicked() {

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
    public void onValidationSucceeded() {
        String email = edit_username_test.getText().toString();
        String password = edit_password_test.getText().toString();
        signInPresenter.callSignIn(email, password);
        this.signInPresenter.navigateToOrderList();

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
