/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.order.presentation.presenter;

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
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class SignUpPresenter implements Presenter, Validator.ValidationListener {

    private SignUpView signUpView;
    private Validator mValidator;

    @Inject
    public SignUpPresenter() {

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
        navigationVerifyPhoneView(name, email, password);
    }

    public void navigationSignIn() {
        if (this.signUpView.activity() instanceof SignUpActivity) {
            ((SignUpActivity) this.signUpView.activity()).navigateToSignIn();
        }
    }

    public void navigationVerifyPhoneView(String name,
                                          String email,
                                          String password) {
        if (this.signUpView.activity() instanceof SignUpActivity) {
            ((SignUpActivity) this.signUpView.activity()).navigateVerifyPhone(name, email, password);
        }
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
