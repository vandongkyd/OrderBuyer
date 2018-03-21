package com.fernandocejas.android10.order.presentation.presenter_buyer;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.domain.Model_buyer.User_Buyer;
import com.fernandocejas.android10.order.domain.interactor_buyer.RegisterUser_Buyer;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.VerifyPhoneView;
import com.fernandocejas.android10.order.presentation.view.activity.SignUpActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by vandongluong on 3/20/18.
 */
@PerActivity
public class VerifyPhonePresenter_Buyer implements Presenter, Validator.ValidationListener {

    private VerifyPhoneView verifyPhoneView;
    private final RegisterUser_Buyer registerUserUseCase;
    private Validator mValidator;

    @Inject
    public VerifyPhonePresenter_Buyer(RegisterUser_Buyer registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }
    public void setView(@NonNull VerifyPhoneView view) {
        this.verifyPhoneView = view;
    }

    @Override
    public void resume() {

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
        this.registerUserUseCase.execute(new VerifyPhonePresenter_Buyer.SignUpObserver(), RegisterUser_Buyer.Params.forUserRegister(
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

    private final class SignUpObserver extends DefaultObserver<User_Buyer> {

        @Override
        public void onComplete() {
            VerifyPhonePresenter_Buyer.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            VerifyPhonePresenter_Buyer.this.hideViewLoading();
            VerifyPhonePresenter_Buyer.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            VerifyPhonePresenter_Buyer.this.showViewRetry();
        }

        @Override
        public void onNext(User_Buyer user) {
            if (user != null) {
                if (user.getStatus().equals(Constants.USER_NOT_ACTIVE)) {
                    VerifyPhonePresenter_Buyer.this.navigationActivation(user.getToken(), user.getPhone());
                }
            }
        }
    }
}
