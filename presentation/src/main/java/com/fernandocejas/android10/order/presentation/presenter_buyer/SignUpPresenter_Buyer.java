package com.fernandocejas.android10.order.presentation.presenter_buyer;


import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.presentation.view.SignUpView;
import com.fernandocejas.android10.order.presentation.view.activity.SignUpActivity;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vandongluong on 3/19/18.
 */
@PerActivity
public class SignUpPresenter_Buyer implements Presenter, Validator.ValidationListener {
    private SignUpView signUpView;
    private com.mobsandgeeks.saripaar.Validator mValidator;


    @Inject
    public SignUpPresenter_Buyer() {

    }

    public void setView(@NonNull SignUpView view) {
        this.signUpView = view;
    }
    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.signUpView = null;
    }

    public void initialize(Object controller) {
        mValidator = new Validator(controller);
        mValidator.setValidationListener(this);
    }

    public void validateInputs() {
        this.signUpView.resetValidate();
        mValidator.validate();
    }

    public void callSignUp(String name,
                           String email,
                           String password) {
        this.signUp(name, email, password);
    }

    private void signUp(String name,
                        String email,
                        String password) {
        //navigationVerifyPhoneView(name, email, password);
    }

    public void navigationSignIn() {
//        if (this.signUpView.activity() instanceof SignUpActivity) {
//            ((SignUpActivity) this.signUpView.activity()).navigateToSignIn();
//        }
    }

    public void navigationVerifyPhoneView(String name,
                                          String email,
                                          String password) {
//        if (this.signUpView.activity() instanceof SignUpActivity) {
//            ((SignUpActivity) this.signUpView.activity()).navigateVerifyPhone(name, email, password);
//        }
    }


    @Override
    public void onValidationSucceeded() {
        this.signUpView.signUp();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        this.signUpView.onValidateError(errors);
    }
}
