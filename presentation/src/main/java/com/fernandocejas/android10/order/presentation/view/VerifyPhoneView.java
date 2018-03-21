/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import android.view.View;

import com.fernandocejas.android10.sample.presentation.view.LoadDataView;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface VerifyPhoneView extends LoadDataView {

    void onActivationButtonClicked(View view);

    void onDeleteButtonClicked();

    void setCodeToView(String codeText);

    void onSendVerificationCodeClicked();

    void onValidateError(List<ValidationError> errors);

    void resetValidate();

    void sendVerificationCode();

    void onBackClicked();

    void showPhoneCode(String phone_code);

    void openPhoneCodeList();
}

