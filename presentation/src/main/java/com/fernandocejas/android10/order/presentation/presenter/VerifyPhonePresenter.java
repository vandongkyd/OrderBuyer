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

import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.domain.interactor.RegisterUser;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.VerifyPhoneView;
import com.fernandocejas.android10.order.presentation.view.activity.SignUpActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
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
public class VerifyPhonePresenter implements Presenter, Validator.ValidationListener {

    private VerifyPhoneView verifyPhoneView;
    private final RegisterUser registerUserUseCase;

    private Validator mValidator;

    @Inject
    public VerifyPhonePresenter(RegisterUser registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    public void setView(@NonNull VerifyPhoneView view) {
        this.verifyPhoneView = view;
    }

    @Override
    public void resume() {
        this.showPhoneCode();
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.registerUserUseCase.dispose();
        this.verifyPhoneView = null;
    }

    public void initialize(Object controller) {
        mValidator = new Validator(controller);
        mValidator.setValidationListener(this);
        String phoneCode = this.getPhoneCode();
        if (phoneCode == null || phoneCode.isEmpty()) {
            this.getPhoneCodeFromDevices();
        }
    }

    private void showViewLoading() {
        this.verifyPhoneView.showLoading();
    }

    private void hideViewLoading() {
        this.verifyPhoneView.hideLoading();
    }

    private void showViewRetry() {
        this.verifyPhoneView.showRetry();
    }

    private void hideViewRetry() {
        this.verifyPhoneView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.verifyPhoneView.context(),
                errorBundle.getException());
        this.verifyPhoneView.showError(errorMessage);
    }

    public void validateInputs() {
        this.verifyPhoneView.resetValidate();
        mValidator.validate();
    }

    public void callSignUp(String name,
                           String email,
                           String phone,
                           String password,
                           String phone_code) {
        this.hideViewRetry();
        this.showViewLoading();
        this.signUp(name, email, phone, password, phone_code);
    }

    private void signUp(String name,
                        String email,
                        String phone,
                        String password,
                        String phone_code) {
        this.registerUserUseCase.execute(new SignUpObserver(), RegisterUser.Params.forUserRegister(
                name,
                email,
                password,
                phone,
                Constants.REGISTER_TYPE,
                phone_code
        ));
    }

    public void navigationActivation(String token, String phone) {
        if (verifyPhoneView.activity() instanceof SignUpActivity) {
            ((SignUpActivity) verifyPhoneView.activity()).navigateToActivation(token, phone);
        }
    }

    public void navigationSignIn() {
        if (verifyPhoneView.activity() instanceof SignUpActivity) {
            ((SignUpActivity) verifyPhoneView.activity()).handleBackPressed();
        }
    }

    public void navigationPhoneCodeList() {
        if (verifyPhoneView.activity() instanceof SignUpActivity) {
            ((SignUpActivity) verifyPhoneView.activity()).navigateToPhoneCodeList();
        }
    }

    @Override
    public void onValidationSucceeded() {
        verifyPhoneView.sendVerificationCode();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        verifyPhoneView.onValidateError(errors);
    }

    public void getPhoneCodeFromDevices() {
        String phoneCode = Utils.getUserCountryFromDevices(verifyPhoneView.context());
        setPhoneCode(phoneCode);
    }

    private String getPhoneCode() {
        if (verifyPhoneView.activity() instanceof SignUpActivity) {
            return ((SignUpActivity) verifyPhoneView.activity()).getPhoneCode();
        }
        return null;
    }

    private void setPhoneCode(String phoneCode) {
        if (verifyPhoneView.activity() instanceof SignUpActivity) {
            ((SignUpActivity) verifyPhoneView.activity()).setPhoneCode(phoneCode);
        }
    }

    public void showPhoneCode() {
        String phoneCode = this.getPhoneCode();
        if (phoneCode != null) {
            verifyPhoneView.showPhoneCode(phoneCode);
        }
    }

    private final class SignUpObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            VerifyPhonePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            VerifyPhonePresenter.this.hideViewLoading();
            VerifyPhonePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            VerifyPhonePresenter.this.showViewRetry();
        }

        @Override
        public void onNext(User user) {
            if (user != null) {
                if (user.getStatus().equals(Constants.USER_NOT_ACTIVE)) {
                    VerifyPhonePresenter.this.navigationActivation(user.getToken(), user.getPhone());
                }
            }
        }
    }

}
